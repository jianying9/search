package com.search.service;

import com.search.config.ActionNames;
import com.search.entity.TaskEntity;
import com.search.localservice.EmployeeLocalService;
import com.search.localservice.TagLocalService;
import com.search.localservice.TaskLocalService;
import com.search.parameter.SourceParameter;
import com.search.task.SearchTaskImpl;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.task.InjectTaskExecutor;
import com.wolf.framework.task.Task;
import com.wolf.framework.task.TaskExecutor;
import com.wolf.framework.worker.context.MessageContext;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.INSERT_SEARCH_TASK,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
importantParameter = {"source", "location", "tag"},
returnParameter = {"taskId", "type", "state", "source", "context", "lastUpdateTime"},
parametersConfigs = {SourceParameter.class, TaskEntity.class},
validateSession = false,
response = true,
description = "新增一个搜索任务")
public class InsertSearchTaskServiceImpl implements Service {

    @InjectLocalService()
    private EmployeeLocalService employeeLocalService;
    //
    @InjectLocalService()
    private TaskLocalService taskLocalService;
    //
    @InjectLocalService()
    private TagLocalService tagLocalService;
    //
    @InjectTaskExecutor
    private TaskExecutor taskExecutor;

    @Override
    public void execute(MessageContext messageContext) {
        String source = messageContext.getParameter("source");
        String location = messageContext.getParameter("location");
        String tag = messageContext.getParameter("tag");
        TaskEntity taskEntity = this.taskLocalService.insertSearchTask(source, location, tag);
        //触发更新任务
        Task task = new SearchTaskImpl(this.taskExecutor, this.taskLocalService, this.tagLocalService, this.employeeLocalService, taskEntity);
        this.taskExecutor.submit(task);
        messageContext.setEntityData(taskEntity);
        messageContext.success();
    }
}
