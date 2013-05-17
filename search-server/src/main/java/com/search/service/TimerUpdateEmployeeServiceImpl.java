package com.search.service;

import com.search.config.ActionNames;
import com.search.entity.EmployeeEntity;
import com.search.localservice.EmployeeLocalService;
import com.search.localservice.TagLocalService;
import com.search.task.UpdateEmployeeTaskImpl;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.dao.condition.Condition;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.dao.condition.OperateTypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.task.InjectTaskExecutor;
import com.wolf.framework.task.Task;
import com.wolf.framework.task.TaskExecutor;
import com.wolf.framework.worker.context.MessageContext;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.TIMER_UPDATE_EMPLOYEE,
parameterTypeEnum = ParameterTypeEnum.NO_PARAMETER,
validateSession = false,
response = true,
description = "生成50个最近1天没有更新的人员的更新任务，包括更新人员信息以及关注信息")
public class TimerUpdateEmployeeServiceImpl implements Service {

    @InjectLocalService()
    private EmployeeLocalService employeeLocalService;
    //
    @InjectLocalService()
    private TagLocalService tagLocalService;
    //
    @InjectTaskExecutor
    private TaskExecutor taskExecutor;

    @Override
    public void execute(MessageContext messageContext) {
        long currentTime = System.currentTimeMillis() - 86400000;
        InquireContext inquireContext = new InquireContext();
        inquireContext.setPageIndex(1);
        inquireContext.setPageSize(100);
        Condition condition = new Condition("lastUpdateTime", OperateTypeEnum.LESS, Long.toString(currentTime));
        inquireContext.addCondition(condition);
        InquireResult<EmployeeEntity> inquireResult = this.employeeLocalService.inquireEmployee(inquireContext);
        if (inquireResult.isEmpty() == false) {
            //更新时间
            List<EmployeeEntity> empEntityList = inquireResult.getResultList();
            List<String> empIdList = new ArrayList<String>(empEntityList.size());
            for (EmployeeEntity employeeEntity : empEntityList) {
                empIdList.add(employeeEntity.getEmpId());
            }
            this.employeeLocalService.batchUpdateTime(empIdList);
            //生成更新任务
            Task task;
            for (EmployeeEntity empEntity : empEntityList) {
                task = new UpdateEmployeeTaskImpl(this.employeeLocalService, this.tagLocalService, empEntity.getEmpId());
                this.taskExecutor.submit(task);
            }
        }
        messageContext.success();
    }
}
