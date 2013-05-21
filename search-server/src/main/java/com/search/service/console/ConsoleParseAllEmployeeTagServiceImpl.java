package com.search.service.console;

import com.search.config.ActionNames;
import com.search.entity.EmployeeEntity;
import com.search.localservice.EmployeeLocalService;
import com.search.localservice.TagLocalService;
import com.search.task.LocalUpdateEmployeeTaskImpl;
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
        actionName = ActionNames.CONSOLE_PARSE_ALL_EMPLOYEE_TAG,
parameterTypeEnum = ParameterTypeEnum.NO_PARAMETER,
validateSession = false,
page = true,
response = true,
description = "重新解析所有用户的标签信息")
public class ConsoleParseAllEmployeeTagServiceImpl implements Service {

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
        InquireContext inquireContext = new InquireContext();
        inquireContext.setPageIndex(1);
        inquireContext.setPageSize(1000);
        Condition condition = new Condition("lastUpdateTime", OperateTypeEnum.EQUAL, "9999999");
        inquireContext.addCondition(condition);
        InquireResult<EmployeeEntity> inquireResult = this.employeeLocalService.inquireEmployee(inquireContext);
        while (inquireResult.isEmpty() == false) {
            List<EmployeeEntity> empEntityList = inquireResult.getResultList();
            List<String> empIdList = new ArrayList<String>(empEntityList.size());
            for (EmployeeEntity employeeEntity : empEntityList) {
                empIdList.add(employeeEntity.getEmpId());
            }
            this.employeeLocalService.batchUpdateTime(empIdList);
            Task task;
            for (EmployeeEntity employeeEntity : empEntityList) {
                task = new LocalUpdateEmployeeTaskImpl(this.employeeLocalService, this.tagLocalService, employeeEntity.getEmpId());
//                this.taskExecutor.submit(task);
                task.run();
            }
            //
            inquireResult = this.employeeLocalService.inquireEmployee(inquireContext);
        }
        messageContext.success();
    }
}
