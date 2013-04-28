package com.spider.entity;

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
 * 第三方源帐号信息
 *
 * @author aladdin
 */
@DaoConfig(
tableName = "SourceSession",
useCache = true)
@ParametersConfig()
public class SourceSessionEntity extends Entity implements Parameter{
    //
    @ColumnConfig(dataTypeEnum = DataTypeEnum.CHAR_60, columnTypeEnum = ColumnTypeEnum.KEY, desc = "sessionId")
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_60, desc = "session：来源名称+帐号")
    private String sessionId;
    //
    @ColumnConfig(dataTypeEnum = DataTypeEnum.CHAR_4000, desc = "cookie信息")
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_4000, desc = "cookie信息", filterTypes = {})
    private String cookie;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.DATE_TIME, desc = "最后更新时间")
    @ColumnConfig(dataTypeEnum = DataTypeEnum.DATE_TIME, desc = "最后更新时间")
    private long lastUpdateTime;

    public String getSessionId() {
        return sessionId;
    }

    public String getCookie() {
        return cookie;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }
    
    @Override
    public String getKeyValue() {
        return this.sessionId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(4, 1);
        map.put("sessionId", this.sessionId);
        map.put("cookie", this.cookie);
        map.put("lastUpdateTime", Long.toString(this.lastUpdateTime));
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.sessionId = entityMap.get("sessionId");
        this.cookie = entityMap.get("cookie");
        this.lastUpdateTime = Long.parseLong(entityMap.get("lastUpdateTime"));
    }
}
