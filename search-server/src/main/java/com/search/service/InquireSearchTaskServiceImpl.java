package com.search.service;

import com.search.config.ActionNames;
import com.search.entity.TaskEntity;
import com.search.localservice.TaskLocalService;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.worker.context.MessageContext;
import java.util.List;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.INQUIRE_SEARCH_TASK,
parameterTypeEnum = ParameterTypeEnum.NO_PARAMETER,
returnParameter = {"taskId", "type", "state", "source", "context", "lastUpdateTime"},
parametersConfigs = {TaskEntity.class},
validateSession = false,
page = true,
response = true,
description = "查询搜索任务")
public class InquireSearchTaskServiceImpl implements Service {

    @InjectLocalService()
    private TaskLocalService taskLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        InquireResult<TaskEntity> inquireResult = this.taskLocalService.inquireSearchTask(messageContext.getPageIndex(), messageContext.getPageSize());
        if(inquireResult.isEmpty() == false) {
            List<TaskEntity> taskEntityList = inquireResult.getResultList();
            messageContext.setEntityListData(taskEntityList);
            messageContext.setPageNum(inquireResult.getPageNum());
            messageContext.setPageTotal(inquireResult.getTotal());
            messageContext.success();
        }
    }
}
