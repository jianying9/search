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
import com.wolf.framework.worker.context.MessageContext;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.GET_FOLLOW_TEXT,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
importantParameter = {"source", "sourceId"},
returnParameter = {"textId"},
parametersConfigs = {SourceParameter.class},
validateSession = false,
response = true,
description = "抓取第三方渠道用户关注信息文本服务")
public class GetFollowTextServiceImpl implements Service {

    @LocalService()
    private SourceLocalService sourceLocalService;
    //
    @LocalService()
    private SpiderDataLocalService spiderDataLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        String sourceId = messageContext.getParameter("sourceId");
        String source = messageContext.getParameter("source");
        SourceEnum sourceEnum = SourceEnum.valueOf(source);
        String text = this.sourceLocalService.getFollowText(sourceEnum, sourceId);
        if (text.length() > 0) {
            StringBuilder textIdPrefixBuilder = new StringBuilder(36);
            textIdPrefixBuilder.append(source).append('_').append(sourceId);
            String textId = textIdPrefixBuilder.toString();
            Map<String, String> insertMap = new HashMap<String, String>(2, 1);
            insertMap.put("id", textId);
            insertMap.put("text", text);
            this.spiderDataLocalService.insertFollowData(insertMap);
            insertMap.put("textId", textId);
            messageContext.setMapData(insertMap);
            messageContext.success();
        }
    }
}
