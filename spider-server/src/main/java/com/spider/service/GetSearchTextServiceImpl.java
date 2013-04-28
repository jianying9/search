package com.spider.service;

import com.spider.config.ActionNames;
import com.spider.config.SourceEnum;
import com.spider.parameter.SourceParameter;
import com.spider.localservice.SourceLocalService;
import com.spider.localservice.SpiderDataLocalService;
import com.wolf.framework.local.LocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.utils.JsonUtils;
import com.wolf.framework.worker.context.MessageContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.GET_SEARCH_TEXT,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
importantParameter = {"tag", "location", "source"},
returnParameter = {"textIdArr"},
parametersConfigs = {SourceParameter.class},
validateSession = false,
response = true,
description = "抓取第三方渠道搜索结果文本服务")
public class GetSearchTextServiceImpl implements Service {

    @LocalService()
    private SourceLocalService sourceLocalService;
    //
    @LocalService()
    private SpiderDataLocalService spiderDataLocalService;
    //

    @Override
    public void execute(MessageContext messageContext) {
        String tag = messageContext.getParameter("tag");
        String location = messageContext.getParameter("location");
        String source = messageContext.getParameter("source");
        SourceEnum sourceEnum = SourceEnum.valueOf(source);
        String text;
        String textId;
        Map<String, String> insertMap;
        List<String> textIdList = new ArrayList<String>(50);
        List<Map<String, String>> insertMapList = new ArrayList<Map<String, String>>(50);
        StringBuilder textIdPrefixBuilder = new StringBuilder(64);
        textIdPrefixBuilder.append(source).append('_').append(location).append('_').append(tag).append('_');
        final String textIdprefix = textIdPrefixBuilder.toString();
        for (int pageIndex = 1; pageIndex < 100; pageIndex++) {
            text = this.sourceLocalService.getSearchText(sourceEnum, location, tag, pageIndex);
            if (text.length() > 0) {
                break;
            } else {
                textId = textIdprefix.concat(Integer.toString(pageIndex));
                insertMap = new HashMap<String, String>(2, 1);
                insertMap.put("id", textId);
                insertMap.put("text", text);
                insertMapList.add(insertMap);
                textIdList.add(textId);
            }
        }
        if (textIdList.isEmpty() == false) {
            this.spiderDataLocalService.batchInsertSearchData(insertMapList);
            String textIdJson = JsonUtils.listToJSON(textIdList);
            Map<String, String> resultMap = new HashMap<String, String>(2, 1);
            resultMap.put("textIdArr", textIdJson);
            messageContext.setMapData(resultMap);
            messageContext.success();
        }
    }
}
