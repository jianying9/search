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
        actionName = ActionNames.PARSE_INFO_TEXT,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
importantParameter = {"source", "textId"},
returnParameter = {"source", "sourceId", "gender", "nickName", "empName", "location", "tag"},
parametersConfigs = {SourceParameter.class},
validateSession = false,
response = true,
description = "解析搜索结果文本服务")
public class ParseInfoTextServiceImpl implements Service {

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
        String sourceId = textId.substring(source.length() + 1);
        SourceEnum sourceEnum = SourceEnum.valueOf(source);
        HSpiderInfoEntity hSpiderInfoEntity = this.spiderDataLocalService.inquireInfoData(textId);
        if (hSpiderInfoEntity != null) {
            String text = hSpiderInfoEntity.getText();
            Map<String, String> dataMap = this.sourceLocalService.parseInfoText(sourceEnum, text);
            if (dataMap != null) {
                dataMap.put("source", source);
                dataMap.put("sourceId", sourceId);
                messageContext.setMapData(dataMap);
                messageContext.success();
            }
        }
    }
}
