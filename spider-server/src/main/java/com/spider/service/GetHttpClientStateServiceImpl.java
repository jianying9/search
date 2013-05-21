package com.spider.service;

import com.spider.config.ActionNames;
import com.spider.localservice.HttpClientLocalService;
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
        actionName = ActionNames.GET_HTTP_CLIENT_STATE,
parameterTypeEnum = ParameterTypeEnum.NO_PARAMETER,
validateSession = false,
response = true,
description = "获取httpClient的状态")
public class GetHttpClientStateServiceImpl implements Service {

    @InjectLocalService()
    private HttpClientLocalService httpClientLocalService;
    //

    @Override
    public void execute(MessageContext messageContext) {
        boolean isReady = this.httpClientLocalService.isReady();
        if (isReady) {
            messageContext.success();
        }
    }
}
