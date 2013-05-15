package com.spider.service;

import com.spider.config.ActionNames;
import com.spider.config.SourceEnum;
import com.spider.localservice.SourceLocalService;
import com.spider.localservice.SpiderDataLocalService;
import com.spider.parameter.SourceParameter;
import com.wolf.framework.local.InjectLocalService;
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
        actionName = ActionNames.GET_FOLLOW,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
importantParameter = {"source", "sourceId"},
returnParameter = {"sourceIdArr"},
parametersConfigs = {SourceParameter.class},
validateSession = false,
response = true,
description = "抓取第三方渠道用户关注信息服务")
public class GetFollowServiceImpl implements Service {

    @InjectLocalService()
    private SourceLocalService sourceLocalService;
    //
    @InjectLocalService()
    private SpiderDataLocalService spiderDataLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        String sourceId = messageContext.getParameter("sourceId");
        String source = messageContext.getParameter("source");
        SourceEnum sourceEnum = SourceEnum.valueOf(source);
        String text = this.sourceLocalService.getFollowText(sourceEnum, sourceId);
        if (text.length() > 0) {
            //解析
            List<String> sourceIdList = this.sourceLocalService.parseFollowText(sourceEnum, text);
            if (sourceIdList.isEmpty() == false) {
                //保存原始页面
                StringBuilder textIdPrefixBuilder = new StringBuilder(36);
                textIdPrefixBuilder.append(source).append('_').append(sourceId);
                String textId = textIdPrefixBuilder.toString();
                Map<String, String> insertMap = new HashMap<String, String>(2, 1);
                insertMap.put("id", textId);
                insertMap.put("text", text);
                this.spiderDataLocalService.insertFollowData(insertMap);
                //返回
                String sourceIdJson = JsonUtils.listToJSON(sourceIdList);
                Map<String, String> resultMap = new HashMap<String, String>(2, 1);
                resultMap.put("sourceIdArr", sourceIdJson);
                messageContext.setMapData(resultMap);
                messageContext.success();
            }
        }
    }
}
