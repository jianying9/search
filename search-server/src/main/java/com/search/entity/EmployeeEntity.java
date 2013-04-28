package com.search.entity;

import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.annotation.ColumnConfig;
import com.wolf.framework.dao.annotation.ColumnTypeEnum;
import com.wolf.framework.dao.annotation.DaoConfig;
import com.wolf.framework.data.DataTypeEnum;
import com.wolf.framework.service.parameter.Parameter;
import com.wolf.framework.service.parameter.ParameterConfig;
import com.wolf.framework.service.parameter.ParametersConfig;
import java.util.HashMap;
import java.util.Map;

/**
 * 人员信息
 *
 * @author aladdin
 */
@DaoConfig(
tableName = "Employee",
useCache = false)
@ParametersConfig()
public class EmployeeEntity extends Entity implements Parameter{
    //
    @ColumnConfig(dataTypeEnum = DataTypeEnum.CHAR_60, columnTypeEnum = ColumnTypeEnum.KEY, desc = "人员id")
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_60, desc = "人员id：来源名称+来源id")
    private String empId;
    //
    @ColumnConfig(dataTypeEnum = DataTypeEnum.CHAR_10, desc = "性别")
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_10, desc = "性别", filterTypes = {})
    private String gender;
    //
    @ColumnConfig(dataTypeEnum = DataTypeEnum.CHAR_32, desc = "昵称")
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_32, desc = "昵称")
    private String nickName;
    //
    @ColumnConfig(dataTypeEnum = DataTypeEnum.CHAR_32, desc = "姓名")
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_32, desc = "姓名")
    private String empName;
    //
    @ColumnConfig(dataTypeEnum = DataTypeEnum.CHAR_32, columnTypeEnum = ColumnTypeEnum.INDEX, desc = "地区")
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_32, desc = "地区")
    private String location;
    //
    @ColumnConfig(dataTypeEnum = DataTypeEnum.CHAR_4000, columnTypeEnum = ColumnTypeEnum.INDEX, desc = "标签")
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_4000, desc = "标签")
    private String tag;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.DATE_TIME, desc = "最后更新时间")
    @ColumnConfig(dataTypeEnum = DataTypeEnum.DATE_TIME, columnTypeEnum = ColumnTypeEnum.INDEX, desc = "最后更新时间")
    private long lastUpdateTime;
    //
    @ColumnConfig(dataTypeEnum = DataTypeEnum.INT, columnTypeEnum = ColumnTypeEnum.INDEX, desc = "状态：0-正常，1-异常")
    private int state;

    public String getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }

    public String getTag() {
        return tag;
    }
    
    public String getGender() {
        return gender;
    }

    public String getNickName() {
        return nickName;
    }

    public String getLocation() {
        return location;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public int getState() {
        return state;
    }
    
    @Override
    public String getKeyValue() {
        return this.empId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(8, 1);
        map.put("empId", this.empId);
        map.put("gender", this.gender);
        map.put("nickName", this.nickName);
        map.put("empName", this.empName);
        map.put("location", this.location);
        map.put("tag", this.tag);
        map.put("lastUpdateTime", Long.toString(this.lastUpdateTime));
        map.put("state", Integer.toString(this.state));
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.empId = entityMap.get("empId");
        this.gender = entityMap.get("gender");
        this.nickName = entityMap.get("nickName");
        this.empName = entityMap.get("empName");
        this.location = entityMap.get("location");
        this.tag = entityMap.get("tag");
        this.lastUpdateTime = Long.parseLong(entityMap.get("lastUpdateTime"));
        this.state = Integer.parseInt(entityMap.get("state"));
    }
}
