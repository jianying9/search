package com.spider.service;

import com.spider.config.ActionNames;
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
        actionName = ActionNames.CHECK_ALL_SOURCE_SESSION,
parameterTypeEnum = ParameterTypeEnum.NO_PARAMETER,
validateSession = false,
response = true,
description = "检测第三方渠道登录信息")
public class CheckAllSourceSessionServiceImpl implements Service {

    @InjectLocalService()
    private SourceLocalService sourceLocalService;
    //

    @Override
    public void execute(MessageContext messageContext) {
        this.sourceLocalService.checkAllSourceSession();
        messageContext.success();
    }
}
