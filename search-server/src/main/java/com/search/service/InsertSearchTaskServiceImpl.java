package com.search.service;

import com.search.config.ActionNames;
import com.search.localservice.TaskLocalService;
import com.search.parameter.SourceParameter;
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
        actionName = ActionNames.INSERT_SEARCH_TASK,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
importantParameter = {"source", "location", "tag"},
parametersConfigs = {SourceParameter.class},
validateSession = false,
response = true,
description = "新增一个搜索任务")
public class InsertSearchTaskServiceImpl implements Service {

    @InjectLocalService()
    private TaskLocalService taskLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        String source = messageContext.getParameter("source");
        String location = messageContext.getParameter("location");
        String tag = messageContext.getParameter("tag");
        this.taskLocalService.insertSearchTask(source, location, tag);
        messageContext.success();
    }
}
