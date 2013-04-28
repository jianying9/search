package com.search.service;

import com.search.config.ActionNames;
import com.search.entity.EmployeeEntity;
import com.search.localservice.EmployeeLocalService;
import com.wolf.framework.local.LocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.worker.context.MessageContext;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.UPDATE_EMPLOYEE,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
importantParameter = {"empId"},
parametersConfigs = {EmployeeEntity.class},
validateSession = false,
response = true,
description = "更新用户信息")
public class UpdateEmployeeServiceImpl implements Service {


    //
    @LocalService()
    private EmployeeLocalService employeeLocalService;

    @Override
    public void execute(MessageContext messageContext) {
//        String empId = messageContext.getParameter("empId");
//        Map<String, String> resultMap = this.sourceLocalService.getInfo(empId);
//        if (resultMap == null) {
//            this.employeeLocalService.updateToException(empId);
//        } else {
//            this.employeeLocalService.update(resultMap);
//            //获取关注对象
//            List<String> followIdList = this.sourceLocalService.getFollow(empId);
//            if (followIdList != null) {
//                this.employeeLocalService.batchInsertEmpId(followIdList);
//            }
//        }
        messageContext.success();
    }
}
