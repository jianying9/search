package com.search.service;

import com.search.config.ActionNames;
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
        actionName = ActionNames.UPDATE_TOP_OLD_EMPLOYEE,
parameterTypeEnum = ParameterTypeEnum.NO_PARAMETER,
validateSession = false,
response = true,
description = "更新最旧的20个用户信息")
public class UpdateTopOldEmployeeServiceImpl implements Service {

    @LocalService()
    private EmployeeLocalService employeeLocalService;

    @Override
    public void execute(MessageContext messageContext) {
//        List<String> empIdList = this.employeeLocalService.inquireTopOld();
//        if (empIdList.isEmpty() == false) {
//            List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>(empIdList.size());
//            Map<String, String> resultMap;
//            List<String> followIdList;
//            for (String empId : empIdList) {
                //获取用户信息
//                resultMap = this.sourceLocalService.getInfo(empId);
//                if (resultMap == null) {
//                    this.employeeLocalService.updateToException(empId);
//                } else {
//                    resultMapList.add(resultMap);
//                }
                //获取关注对象
//                followIdList = this.sourceLocalService.getFollow(empId);
//                if(followIdList != null) {
//                    this.employeeLocalService.batchInsertEmpId(followIdList);
//                }
//                try {
//                    Thread.currentThread().sleep(2500);
//                } catch (InterruptedException ex) {
//                }
//            }
//            if (resultMapList.isEmpty() == false) {
//                this.employeeLocalService.batchUpdate(resultMapList);
//            }
//        }
        messageContext.success();
    }
}
