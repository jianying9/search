package com.spider.service;

import com.spider.config.ActionNames;
import com.spider.entity.SourceSessionEntity;
import com.spider.localservice.SourceLocalService;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.worker.context.MessageContext;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.CHECK_SOURCE_SESSION,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
importantParameter = {"sessionId"},
parametersConfigs = {SourceSessionEntity.class},
validateSession = false,
response = true,
description = "检测某个第三方渠道登录信息")
public class CheckSourceSessionServiceImpl implements Service {

    @InjectLocalService()
    private SourceLocalService sourceLocalService;
    //

    @Override
    public void execute(MessageContext messageContext) {
        String sessionId = messageContext.getParameter("sessionId");
        this.sourceLocalService.checkSourceSession(sessionId);
        messageContext.success();
    }
}
