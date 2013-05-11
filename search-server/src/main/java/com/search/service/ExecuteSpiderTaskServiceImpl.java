package com.search.service;

import com.search.config.ActionNames;
import com.search.entity.TaskEntity;
import com.search.localservice.TaskLocalService;
import com.search.task.SpiderFollowTaskImpl;
import com.search.task.SpiderInfoTaskImpl;
import com.search.task.SpiderSearchTaskImpl;
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
        actionName = ActionNames.EXECUTE_SPIDER_TASK,
parameterTypeEnum = ParameterTypeEnum.NO_PARAMETER,
page = true,
validateSession = false,
response = true,
description = "执行爬虫任务")
public class ExecuteSpiderTaskServiceImpl implements Service {

    @InjectLocalService()
    private TaskLocalService taskLocalService;
    //
    @InjectTaskExecutor
    private TaskExecutor taskExecutor;

    @Override
    public void execute(MessageContext messageContext) {
        List<TaskEntity> taskEntityList = this.taskLocalService.inquireSpiderTask();
        if (taskEntityList.isEmpty() == false) {
            Task task;
            for (TaskEntity taskEntity : taskEntityList) {
                switch (taskEntity.getType()) {
                    case TaskLocalService.TYPE_SEARCH:
                        task = new SpiderSearchTaskImpl(this.taskLocalService, taskEntity);
                        break;
                    case TaskLocalService.TYPE_INFO:
                        task = new SpiderInfoTaskImpl(this.taskLocalService, taskEntity);
                        break;
                    case TaskLocalService.TYPE_FOLLOW:
                        task = new SpiderFollowTaskImpl(this.taskLocalService, taskEntity);
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
