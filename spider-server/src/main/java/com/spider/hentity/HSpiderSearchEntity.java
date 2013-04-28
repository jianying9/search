package com.spider.hentity;

import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.annotation.HColumnConfig;
import com.wolf.framework.dao.annotation.HDaoConfig;
import java.util.HashMap;
import java.util.Map;

/**
 * 爬虫搜索结果实体
 *
 * @author aladdin
 */
@HDaoConfig(
tableName = "SpiderSearch")
public class HSpiderSearchEntity extends Entity {
    //

    @HColumnConfig(key = true, desc = "记录ID")
    private String id;
    //
    @HColumnConfig(desc = "文本")
    private String text;

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public String getKeyValue() {
        return this.id;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(2, 1);
        map.put("searchId", this.id);
        map.put("text", this.text);
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.id = entityMap.get("id");
        this.text = entityMap.get("text");
    }
}
