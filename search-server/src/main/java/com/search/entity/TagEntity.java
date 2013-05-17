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
 * 标签信息
 *
 * @author aladdin
 */
@DaoConfig(
tableName = "Tag",
useCache = false)
@ParametersConfig()
public class TagEntity extends Entity implements Parameter {
    //

    @ColumnConfig(dataTypeEnum = DataTypeEnum.CHAR_32, columnTypeEnum = ColumnTypeEnum.KEY, desc = "标签")
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_32, desc = "标签")
    private String tag;
    //
    @ColumnConfig(dataTypeEnum = DataTypeEnum.INT, columnTypeEnum = ColumnTypeEnum.INDEX, desc = "总数")
    @ParameterConfig(dateTypeEnum = DataTypeEnum.INT, desc = "总数")
    private int total;
    //
    @ColumnConfig(dataTypeEnum = DataTypeEnum.INT, columnTypeEnum = ColumnTypeEnum.INDEX, desc = "状态：0-无变化,1-有变化，待更新")
    @ParameterConfig(dateTypeEnum = DataTypeEnum.INT, desc = "状态：0-无变化,1-有变化，待更新")
    private int state;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.DATE_TIME, desc = "最后更新时间")
    @ColumnConfig(dataTypeEnum = DataTypeEnum.DATE_TIME, columnTypeEnum = ColumnTypeEnum.INDEX, desc = "最后更新时间")
    private long lastUpdateTime;

    public String getTag() {
        return tag;
    }

    public int getTotal() {
        return total;
    }

    public int getState() {
        return state;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    @Override
    public String getKeyValue() {
        return this.tag;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(4, 1);
        map.put("tag", this.tag);
        map.put("total", Integer.toString(this.total));
        map.put("state", Integer.toString(this.state));
        map.put("lastUpdateTime", Long.toString(this.lastUpdateTime));
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.tag = entityMap.get("tag");
        this.total = Integer.parseInt(entityMap.get("total"));
        this.state = Integer.parseInt(entityMap.get("state"));
        this.lastUpdateTime = Long.parseLong(entityMap.get("lastUpdateTime"));
    }
}
