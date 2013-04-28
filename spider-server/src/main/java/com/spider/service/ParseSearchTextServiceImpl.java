package com.spider.service;

import com.spider.config.ActionNames;
import com.spider.config.SourceEnum;
import com.spider.hentity.HSpiderSearchEntity;
import com.spider.parameter.SourceParameter;
import com.spider.localservice.SourceLocalService;
import com.spider.localservice.SpiderDataLocalService;
import com.wolf.framework.local.LocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.utils.JsonUtils;
import com.wolf.framework.worker.context.MessageContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.PARSE_SEARCH_TEXT,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
importantParameter = {"source", "textId"},
returnParameter = {"sourceIdArr"},
parametersConfigs = {SourceParameter.class},
validateSession = false,
response = true,
description = "解析搜索结果文本服务")
public class ParseSearchTextServiceImpl implements Service {

    @LocalService()
    private SourceLocalService sourceLocalService;
    //
    @LocalService()
    private SpiderDataLocalService spiderDataLocalService;
    //

    @Override
    public void execute(MessageContext messageContext) {
        Map<String, String> parameterMap = messageContext.getParameterMap();
        String textId = parameterMap.get("textId");
        String source = parameterMap.get("source");
        SourceEnum sourceEnum = SourceEnum.valueOf(source);
        HSpiderSearchEntity hSpiderSearchEntity = this.spiderDataLocalService.inquireSearchData(textId);
        if (hSpiderSearchEntity != null) {
            String text = hSpiderSearchEntity.getText();
            List<String> sourceIdList = this.sourceLocalService.parseSearchText(sourceEnum, text);
            if (sourceIdList.isEmpty() == false) {
                String sourceIdJson = JsonUtils.listToJSON(sourceIdList);
                Map<String, String> resultMap = new HashMap<String, String>(2, 1);
                resultMap.put("sourceIdArr", sourceIdJson);
                messageContext.setMapData(resultMap);
                messageContext.success();
            }
        }
    }
}
