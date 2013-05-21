package com.spider.service.timer;

import com.spider.config.ActionNames;
import com.spider.localservice.HttpClientLocalService;
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
        actionName = ActionNames.TIMER_CHECK_ALL_SOURCE_SESSION,
parameterTypeEnum = ParameterTypeEnum.NO_PARAMETER,
validateSession = false,
response = true,
description = "检测第三方渠道登录信息")
public class TimerCheckAllSourceSessionServiceImpl implements Service {

    @InjectLocalService()
    private SourceLocalService sourceLocalService;
    //
    @InjectLocalService()
    private HttpClientLocalService httpClientLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        this.httpClientLocalService.unready();
        this.sourceLocalService.checkAllSourceSession();
        this.httpClientLocalService.init();
        this.httpClientLocalService.isReady();
        messageContext.success();
    }
}
