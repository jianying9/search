package com.spider.service;

import com.spider.config.ActionNames;
import com.spider.config.SourceEnum;
import com.spider.entity.SourceSessionEntity;
import com.spider.parameter.SourceParameter;
import com.spider.localservice.SourceLocalService;
import com.wolf.framework.local.LocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.worker.context.MessageContext;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.INSERT_SOURCE_SESSION,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
importantParameter = {"source", "userName", "password"},
parametersConfigs = {SourceParameter.class, SourceSessionEntity.class},
validateSession = false,
response = true,
description = "增加第三方渠道session")
public class InsertSourceSessionServiceImpl implements Service {

    //
    @LocalService()
    private SourceLocalService sourceLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        String source = messageContext.getParameter("source");
        //远程登录获取cookie
        String userName = messageContext.getParameter("userName");
        String password = messageContext.getParameter("password");
        SourceEnum sourceEnum = SourceEnum.valueOf(source);
        this.sourceLocalService.insertLoginSession(sourceEnum, userName, password);
        messageContext.success();

    }
}
