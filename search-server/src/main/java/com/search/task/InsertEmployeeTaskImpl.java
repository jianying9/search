package com.search.task;

import com.search.entity.EmployeeEntity;
import com.search.localservice.EmployeeLocalService;
import com.search.localservice.TagLocalService;
import com.wolf.framework.task.Task;
import java.util.Map;

/**
 *
 * @author aladdin
 */
public class InsertEmployeeTaskImpl extends AbstractEmployeeTask implements Task {

    public InsertEmployeeTaskImpl(EmployeeLocalService employeeLocalService, TagLocalService tagLocalService, String empId) {
        super(employeeLocalService, tagLocalService, empId);
    }

    @Override
    protected void saveEmployee(Map<String, String> dataMap) {
        EmployeeEntity empEntity = this.employeeLocalService.inquireByEmpId(empId);
        if (empEntity == null) {
            this.employeeLocalService.insert(dataMap);
        } else {
            this.employeeLocalService.update(dataMap);
        }
    }
}
