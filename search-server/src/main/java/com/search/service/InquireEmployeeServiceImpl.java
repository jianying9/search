package com.search.service;

import com.search.config.ActionNames;
import com.search.entity.EmployeeEntity;
import com.search.localservice.EmployeeLocalService;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.dao.condition.Condition;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.dao.condition.OperateTypeEnum;
import com.wolf.framework.dao.condition.Order;
import com.wolf.framework.dao.condition.OrderTypeEnum;
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
        actionName = ActionNames.INQUIRE_EMPLOYEE,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
importantParameter = {"tag"},
returnParameter = {"empId", "gender", "nickName", "empName", "location", "tag", "lastUpdateTime"},
parametersConfigs = {EmployeeEntity.class},
validateSession = false,
page = true,
response = true,
description = "查询人员任务")
public class InquireEmployeeServiceImpl implements Service {
    
    @InjectLocalService()
    private EmployeeLocalService employeeLocalService;
    
    @Override
    public void execute(MessageContext messageContext) {
        String tag = messageContext.getParameter("tag");
        //小写
        tag = tag.toLowerCase();
        InquireContext inquireContext = new InquireContext();
        inquireContext.setPageIndex(messageContext.getPageIndex());
        inquireContext.setPageSize(messageContext.getPageSize());
        Condition condition;
        condition = new Condition("tag", OperateTypeEnum.LIKE, tag);
        inquireContext.addCondition(condition);
        Order order = new Order("lastUpdateTime", OrderTypeEnum.DESC);
        inquireContext.addOrder(order);
        InquireResult<EmployeeEntity> inquireResult = this.employeeLocalService.inquireEmployee(inquireContext);
        if (inquireResult.isEmpty() == false) {
            List<EmployeeEntity> empEntityList = inquireResult.getResultList();
            messageContext.setPageTotal(inquireResult.getTotal());
            messageContext.setPageNum(inquireResult.getPageNum());
            messageContext.setEntityListData(empEntityList);
            messageContext.success();
        }
    }
}
