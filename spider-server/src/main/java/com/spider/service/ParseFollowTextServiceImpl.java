package com.spider.service;

import com.spider.config.ActionNames;
import com.spider.config.SourceEnum;
import com.spider.hentity.HSpiderFollowEntity;
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
        actionName = ActionNames.PARSE_FOLLOW_TEXT,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
importantParameter = {"source", "textId"},
returnParameter = {"sourceIdArr"},
parametersConfigs = {SourceParameter.class},
validateSession = false,
response = true,
description = "解析个人关注信息文本服务")
public class ParseFollowTextServiceImpl implements Service {

    @InjectLocalService()
    private SourceLocalService sourceLocalService;
    //
    @InjectLocalService()
    private SpiderDataLocalService spiderDataLocalService;
    //

    @Override
    public void execute(MessageContext messageContext) {
        Map<String, String> parameterMap = messageContext.getParameterMap();
        String textId = parameterMap.get("textId");
        String source = parameterMap.get("source");
        SourceEnum sourceEnum = SourceEnum.valueOf(source);
        HSpiderFollowEntity hSpiderFollowEntity = this.spiderDataLocalService.inquireFollowData(textId);
        if (hSpiderFollowEntity != null) {
            String text = hSpiderFollowEntity.getText();
            List<String> sourceIdList = this.sourceLocalService.parseFollowText(sourceEnum, text);
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
