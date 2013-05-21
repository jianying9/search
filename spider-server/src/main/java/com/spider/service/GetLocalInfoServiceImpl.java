package com.spider.service;

import com.spider.config.ActionNames;
import com.spider.config.SourceEnum;
import com.spider.hentity.HSpiderInfoEntity;
import com.spider.localservice.SourceLocalService;
import com.spider.localservice.SpiderDataLocalService;
import com.spider.parameter.SourceParameter;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.worker.context.MessageContext;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.GET_LOCAL_INFO,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
importantParameter = {"source", "sourceId"},
returnParameter = {"source", "sourceId", "gender", "nickName", "empName", "location", "tag"},
parametersConfigs = {SourceParameter.class},
validateSession = false,
response = true,
description = "从本地获取第三方渠道用户信息服务")
public class GetLocalInfoServiceImpl implements Service {

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
        StringBuilder textIdPrefixBuilder = new StringBuilder(36);
        textIdPrefixBuilder.append(source).append('_').append(sourceId);
        String textId = textIdPrefixBuilder.toString();
        HSpiderInfoEntity hSpiderInfoEntity = this.spiderDataLocalService.inquireInfoData(textId);
        if (hSpiderInfoEntity != null) {
            //解析
            Map<String, String> dataMap = this.sourceLocalService.parseInfoText(sourceEnum, hSpiderInfoEntity.getText());
            if (dataMap != null) {
                //返回
                dataMap.put("source", source);
                dataMap.put("sourceId", sourceId);
                messageContext.setMapData(dataMap);
                messageContext.success();
            }
        }
    }
}
