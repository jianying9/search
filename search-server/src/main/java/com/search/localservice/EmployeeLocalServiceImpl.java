package com.search.localservice;

import com.search.entity.EmployeeEntity;
import com.wolf.framework.dao.EntityDao;
import com.wolf.framework.dao.InquireKeyResult;
import com.wolf.framework.dao.annotation.DAO;
import com.wolf.framework.dao.condition.Condition;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.dao.condition.OperateTypeEnum;
import com.wolf.framework.dao.condition.Order;
import com.wolf.framework.dao.condition.OrderTypeEnum;
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

    @DAO(clazz = EmployeeEntity.class)
    private EntityDao<EmployeeEntity> employeeEntityDao;

    @Override
    public void batchInsertEmpId(List<String> empIdList) {
        EmployeeEntity empEntity;
        Map<String, String> empMap;
        List<Map<String, String>> empMapList = new ArrayList<Map<String, String>>(empIdList.size());
        for (String empId : empIdList) {
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
    public List<String> inquireTopOld() {
        Condition condition = new Condition("state", OperateTypeEnum.EQUAL, "0");
        Order order = new Order("lastUpdateTime", OrderTypeEnum.ASC);
        InquireContext inquireContext = new InquireContext();
        inquireContext.setPageIndex(1);
        inquireContext.setPageSize(20);
        inquireContext.addCondition(condition);
        inquireContext.addOrder(order);
        InquireKeyResult inquireKeyResult = this.employeeEntityDao.inquirePageKeysByCondition(inquireContext);
        return inquireKeyResult.getResultList();
    }

    @Override
    public void batchUpdate(List<Map<String, String>> updateMapList) {
        for (Map<String, String> updateMap : updateMapList) {
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
    public void update(Map<String, String> updateMap) {
        updateMap.put("state", "0");
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
}
