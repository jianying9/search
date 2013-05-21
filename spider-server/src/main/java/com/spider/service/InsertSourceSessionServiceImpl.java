package com.spider.service;

import com.spider.config.ActionNames;
import com.spider.config.SourceEnum;
import com.spider.localservice.SourceSessionLocalService;
import com.spider.parameter.SourceParameter;
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
        actionName = ActionNames.INSERT_SOURCE_SESSION,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
importantParameter = {"source", "userName"},
parametersConfigs = {SourceParameter.class},
validateSession = false,
response = true,
description = "增加第三方渠道session")
public class InsertSourceSessionServiceImpl implements Service {

    //
    @InjectLocalService()
    private SourceSessionLocalService sourceSessionLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        String source = messageContext.getParameter("source");
        //远程登录获取cookie
        String userName = messageContext.getParameter("userName");
        SourceEnum sourceEnum = SourceEnum.valueOf(source);
        this.sourceSessionLocalService.insertSourceSession(sourceEnum, userName, "");
        messageContext.success();
    }
}
