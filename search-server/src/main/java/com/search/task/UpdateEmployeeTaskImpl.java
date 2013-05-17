package com.search.task;

import com.search.localservice.EmployeeLocalService;
import com.search.localservice.TagLocalService;
import com.wolf.framework.task.Task;
import java.util.Map;

/**
 *
 * @author aladdin
 */
public class UpdateEmployeeTaskImpl extends AbstractEmployeeTask implements Task {

    public UpdateEmployeeTaskImpl(EmployeeLocalService employeeLocalService, TagLocalService tagLocalService, String empId) {
        super(employeeLocalService, tagLocalService, empId);
    }

    @Override
    protected void saveEmployee(Map<String, String> dataMap) {
        this.employeeLocalService.update(dataMap);
    }
}
