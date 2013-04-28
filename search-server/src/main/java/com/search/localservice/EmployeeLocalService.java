package com.search.localservice;

import com.wolf.framework.local.Local;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
public interface EmployeeLocalService extends Local{
    
    /**
     * 批量写入empId
     * @param empIdList 
     */
    public void batchInsertEmpId(List<String> empIdList);
    
    /**
     * 查询最旧的20个人员信息
     * @return 
     */
    public List<String> inquireTopOld();
    
    /**
     * 更新一个用户信息
     * @param updateMap 
     */
    public void update(Map<String, String> updateMap);
    
    /**
     * 批量更新人员信息
     * @param updateMapList 
     */
    public void batchUpdate(List<Map<String, String>> updateMapList);
    
    /**
     * 将某个用户标记为异常状态
     * @param empId 
     */
    public void updateToException(String empId);
    
    public String createEmpId(String source, String sourceId);
    
    public String[] parseEmpId(String empId);
}
