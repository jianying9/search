package com.search.localservice;

import com.search.entity.EmployeeEntity;
import com.wolf.framework.dao.EntityDao;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.dao.annotation.InjectDao;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.local.LocalServiceConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@LocalServiceConfig(
        interfaceInfo = EmployeeLocalService.class,
description = "负责操作employee表")
public class EmployeeLocalServiceImpl implements EmployeeLocalService {

    @InjectDao(clazz = EmployeeEntity.class)
    private EntityDao<EmployeeEntity> employeeEntityDao;

    @Override
    public void batchInsertEmployee(String source, List<String> sourceIdList) {
        List<Map<String, String>> empMapList = new ArrayList<Map<String, String>>(500);
        EmployeeEntity empEntity;
        Map<String, String> empMap;
        String empId;
        for (String sourceId : sourceIdList) {
            empId = this.createEmpId(source, sourceId);
            empEntity = this.employeeEntityDao.inquireByKey(empId);
            if (empEntity == null) {
                empMap = new HashMap<String, String>(8, 1);
                empMap.put("empId", empId);
                empMap.put("gender", "");
                empMap.put("nickName", "");
                empMap.put("empName", "");
                empMap.put("location", "");
                empMap.put("tag", "");
                empMap.put("lastUpdateTime", Long.toString(System.currentTimeMillis()));
                empMap.put("state", "0");
                empMapList.add(empMap);
            }
        }
        if (empMapList.isEmpty() == false) {
            this.employeeEntityDao.batchInsert(empMapList);
        }
    }

    @Override
    public void batchUpdate(String source, List<Map<String, String>> updateMapList) {
        String empId;
        String sourceId;
        for (Map<String, String> updateMap : updateMapList) {
            sourceId = updateMap.get("sourceId");
            empId = this.createEmpId(source, sourceId);
            updateMap.put("empId", empId);
            updateMap.put("state", "0");
            updateMap.put("lastUpdateTime", Long.toString(System.currentTimeMillis()));
        }
        this.employeeEntityDao.batchUpdate(updateMapList);
    }

    @Override
    public void updateToException(String empId) {
        Map<String, String> updateMap = new HashMap<String, String>(4, 1);
        updateMap.put("empId", empId);
        updateMap.put("state", "1");
        updateMap.put("lastUpdateTime", Long.toString(System.currentTimeMillis()));
        this.employeeEntityDao.update(updateMap);
    }

    @Override
    public String createEmpId(String source, String sourceId) {
        StringBuilder empIdBuilder = new StringBuilder(36);
        empIdBuilder.append(source).append('_').append(sourceId);
        return empIdBuilder.toString();
    }

    @Override
    public String[] parseEmpId(String empId) {
        int index = empId.indexOf("_");
        String source = empId.substring(0, index);
        String userName = empId.substring(index + 1);
        String[] result = {source, userName};
        return result;
    }

    @Override
    public InquireResult<EmployeeEntity> inquireEmployee(InquireContext inquireContext) {
        return this.employeeEntityDao.inquirePageByCondition(inquireContext);
    }
}
