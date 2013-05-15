package com.search.service;

import com.search.config.ActionNames;
import com.search.parameter.TimerParameter;
import com.search.timer.TimerState;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.worker.context.MessageContext;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.TIMER_MANAGE,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
importantParameter = {"timerState", "option"},
parametersConfigs = {TimerParameter.class},
validateSession = false,
response = true,
description = "定时任务开关控制")
public class TimerManageServiceImpl implements Service {

    @Override
    public void execute(MessageContext messageContext) {
        String timerState = messageContext.getParameter("timerState");
        String option = messageContext.getParameter("option");
        if(timerState.equals("TIMER_UPDATE_EMPLOYEE_STATE")) {
            if(option.equals("start")) {
                TimerState.TIMER_UPDATE_EMPLOYEE_STATE = TimerState.STATE_START;
            } else {
                TimerState.TIMER_UPDATE_EMPLOYEE_STATE = TimerState.STATE_STOP;
            }
        }
        messageContext.success();
    }
}
