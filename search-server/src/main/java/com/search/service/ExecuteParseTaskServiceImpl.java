package com.search.service;

import com.search.config.ActionNames;
import com.search.entity.TaskEntity;
import com.search.localservice.EmployeeLocalService;
import com.search.localservice.TaskLocalService;
import com.search.task.ParseFollowTextTaskImpl;
import com.search.task.ParseInfoTextTaskImpl;
import com.search.task.ParseSearchTextTaskImpl;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.task.InjectTaskExecutor;
import com.wolf.framework.task.Task;
import com.wolf.framework.task.TaskExecutor;
import com.wolf.framework.worker.context.MessageContext;
import java.util.List;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.EXECUTE_PARSE_TASK,
parameterTypeEnum = ParameterTypeEnum.NO_PARAMETER,
page = true,
validateSession = false,
response = true,
description = "执行解析任务")
public class ExecuteParseTaskServiceImpl implements Service {
    
    @InjectLocalService()
    private EmployeeLocalService employeeLocalService;
    //
    @InjectLocalService()
    private TaskLocalService taskLocalService;
    //
    @InjectTaskExecutor
    private TaskExecutor taskExecutor;

    @Override
    public void execute(MessageContext messageContext) {
        InquireResult<TaskEntity> inquireResult = this.taskLocalService.inquireParseTask(messageContext.getPageIndex(), messageContext.getPageSize());
        if (inquireResult.isEmpty() == false) {
            List<TaskEntity> taskEntityList = inquireResult.getResultList();
            Task task;
            for (TaskEntity taskEntity : taskEntityList) {
                switch(taskEntity.getType()) {
                    case TaskLocalService.TYPE_SEARCH:
                        task = new ParseSearchTextTaskImpl(this.taskLocalService, this.employeeLocalService, taskEntity);
                        break;
                    case TaskLocalService.TYPE_INFO:
                        task = new ParseInfoTextTaskImpl(this.taskLocalService, this.employeeLocalService, taskEntity);
                        break;
                    case TaskLocalService.TYPE_FOLLOW:
                        task = new ParseFollowTextTaskImpl(this.taskLocalService, this.employeeLocalService, taskEntity);
                        break;
                    default:
                        task = null;
                }
                this.taskExecutor.submet(task);
            }
            messageContext.success();
        }
    }
}
