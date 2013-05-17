package com.search.localservice;

import com.search.entity.EmployeeEntity;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.local.Local;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
public interface EmployeeLocalService extends Local {
    
    public int STATE_NORMAL = 0;
    
    public int STATE_EXCEPTION = 1;
    
    public void batchInsertEmployee(String source, List<String> sourceIdList);
    
    public void insert(Map<String, String> insertMap);
    
    public void batchUpdateTime(List<String> empIdList);
    
    public void update(Map<String, String> updateMap);

    /**
     * 批量更新人员信息
     *
     * @param updateMapList
     */
    public void batchUpdate(String source, List<Map<String, String>> updateMapList);

    /**
     * 将某个用户标记为异常状态
     *
     * @param empId
     */
    public void updateToException(String empId);

    public String createEmpId(String source, String sourceId);

    public String[] parseEmpId(String empId);

    public InquireResult<EmployeeEntity> inquireEmployee(InquireContext inquireContext);
    
    public EmployeeEntity inquireByEmpId(String empId);
    
    public int countByTag(String tag);
}
