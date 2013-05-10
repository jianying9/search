package com.search.service;

import com.search.config.ActionNames;
import com.search.entity.EmployeeEntity;
import com.search.localservice.EmployeeLocalService;
import com.search.localservice.TaskLocalService;
import com.search.task.InsertUpdateTaskTaskImpl;
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
import com.wolf.framework.task.InjectTaskExecutor;
import com.wolf.framework.task.Task;
import com.wolf.framework.task.TaskExecutor;
import com.wolf.framework.worker.context.MessageContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.INSERT_UPDATE_TASK,
parameterTypeEnum = ParameterTypeEnum.NO_PARAMETER,
page = true,
validateSession = false,
response = true,
description = "新增一个更新任务，包括更新人员信息以及关注信息")
public class InsertUpdateTaskServiceImpl implements Service {

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
        InquireContext inquireContext = new InquireContext();
        inquireContext.setPageIndex(messageContext.getPageIndex());
        inquireContext.setPageSize(messageContext.getPageSize());
        Condition condition;
        condition = new Condition("state", OperateTypeEnum.EQUAL, Integer.toString(EmployeeLocalService.STATE_NORMAL));
        inquireContext.addCondition(condition);
        Order order = new Order("lastUpdateTime", OrderTypeEnum.ASC);
        inquireContext.addOrder(order);
        InquireResult<EmployeeEntity> inquireResult = this.employeeLocalService.inquireEmployee(inquireContext);
        if (inquireResult.isEmpty() == false) {
            //分类
            Map<String, List<String>> sourceMap = new HashMap<String, List<String>>(4, 1);
            List<EmployeeEntity> empEntityList = inquireResult.getResultList();
            String sourceId;
            String[] sourceInfo;
            String source;
            List<String> sourceIdList;
            for (EmployeeEntity employeeEntity : empEntityList) {
                sourceInfo = this.employeeLocalService.parseEmpId(employeeEntity.getEmpId());
                source = sourceInfo[0];
                sourceId = sourceInfo[1];
                sourceIdList = sourceMap.get(source);
                if (sourceIdList == null) {
                    sourceIdList = new ArrayList<String>(20);
                    sourceMap.put(source, sourceIdList);
                }
                sourceIdList.add(sourceId);
            }
            //创建任务
            Task task;
            Set<Map.Entry<String, List<String>>> entrySet = sourceMap.entrySet();
            for (Map.Entry<String, List<String>> entry : entrySet) {
                source = entry.getKey();
                sourceIdList = entry.getValue();
                task = new InsertUpdateTaskTaskImpl(this.taskLocalService, source, sourceIdList);
                this.taskExecutor.submet(task);
            }
            messageContext.success();
        }
    }
}
