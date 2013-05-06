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
 * 任务信息
 *
 * @author aladdin
 */
@DaoConfig(
tableName = "Task",
useCache = false)
@ParametersConfig()
public class TaskEntity extends Entity implements Parameter {
    //

    @ColumnConfig(dataTypeEnum = DataTypeEnum.UUID, columnTypeEnum = ColumnTypeEnum.KEY, desc = "任务ID")
    @ParameterConfig(dateTypeEnum = DataTypeEnum.UUID, desc = "任务ID")
    private String taskId;
    //
    @ColumnConfig(dataTypeEnum = DataTypeEnum.INT, columnTypeEnum = ColumnTypeEnum.INDEX, desc = "任务类型:1-search,2-info,3-follow")
    @ParameterConfig(dateTypeEnum = DataTypeEnum.INT, desc = "任务类型:1-search,2-info,3-follow", filterTypes = {})
    private int type;
    //
    @ColumnConfig(dataTypeEnum = DataTypeEnum.INT, columnTypeEnum = ColumnTypeEnum.INDEX, desc = "状态：1-抓取,2-解析,0-完成")
    @ParameterConfig(dateTypeEnum = DataTypeEnum.INT, desc = "状态：1-抓取,2-解析,0-完成")
    private int state;
    //
    @ColumnConfig(dataTypeEnum = DataTypeEnum.CHAR_10, desc = "渠道")
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_10, desc = "渠道")
    private String source;
    //
    @ColumnConfig(dataTypeEnum = DataTypeEnum.CHAR_4000, desc = "上下文参数")
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_4000, desc = "上下文参数")
    private String context;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.DATE_TIME, desc = "最后更新时间")
    @ColumnConfig(dataTypeEnum = DataTypeEnum.DATE_TIME, columnTypeEnum = ColumnTypeEnum.INDEX, desc = "最后更新时间")
    private long lastUpdateTime;

    public String getTaskId() {
        return taskId;
    }

    public int getType() {
        return type;
    }

    public int getState() {
        return state;
    }

    public String getSource() {
        return source;
    }
    
    public String getContext() {
        return context;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    @Override
    public String getKeyValue() {
        return this.taskId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(8, 1);
        map.put("taskId", this.taskId);
        map.put("type", Integer.toString(this.type));
        map.put("state", Integer.toString(this.state));
        map.put("source", this.source);
        map.put("context", this.context);
        map.put("lastUpdateTime", Long.toString(this.lastUpdateTime));
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.taskId = entityMap.get("taskId");
        this.type = Integer.parseInt(entityMap.get("type"));
        this.state = Integer.parseInt(entityMap.get("state"));
        this.source = entityMap.get("source");
        this.context = entityMap.get("context");
        this.lastUpdateTime = Long.parseLong(entityMap.get("lastUpdateTime"));
    }
}
