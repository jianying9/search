package com.search.localservice;

import com.search.entity.TagEntity;
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
        interfaceInfo = TagLocalService.class,
description = "负责操作tag表")
public class TagLocalServiceImpl implements TagLocalService {

    @InjectDao(clazz = TagEntity.class)
    private EntityDao<TagEntity> tagEntityDao;

    @Override
    public void init() {
    }

    @Override
    public TagEntity inquireByTag(String tag) {
        return this.tagEntityDao.inquireByKey(tag);
    }

    @Override
    public void batchInsert(List<String> tagList) {
        Map<String, String> insertMap;
        List<Map<String, String>> insertMapList = new ArrayList<Map<String, String>>(tagList.size());
        String state = Integer.toString(TagLocalService.STATE_NEW);
        String lastUpdateTime = "1000";
        for (String tag : tagList) {
            insertMap = new HashMap<String, String>(4, 1);
            insertMap.put("tag", tag);
            insertMap.put("total", "0");
            insertMap.put("state", state);
            insertMap.put("lastUpdateTime", lastUpdateTime);
            insertMapList.add(insertMap);
        }
        this.tagEntityDao.batchInsert(insertMapList);
    }

    @Override
    public void batchUpdateNewState(List<String> tagList) {
        Map<String, String> updateMap;
        List<Map<String, String>> updateMapList = new ArrayList<Map<String, String>>(tagList.size());
        String state = Integer.toString(TagLocalService.STATE_NEW);
        for (String tag : tagList) {
            updateMap = new HashMap<String, String>(2, 1);
            updateMap.put("tag", tag);
            updateMap.put("state", state);
            updateMapList.add(updateMap);
        }
        this.tagEntityDao.batchUpdate(updateMapList);
    }

    @Override
    public void batchUpdate(List<Map<String, String>> updateMapList) {
        String lastUpdateTime = Long.toString(System.currentTimeMillis());
        for (Map<String, String> updateMap : updateMapList) {
            updateMap.put("lastUpdateTime", lastUpdateTime);
        }
        this.tagEntityDao.batchUpdate(updateMapList);
    }

    @Override
    public InquireResult<TagEntity> inquireTag(InquireContext inquireContext) {
        return this.tagEntityDao.inquirePageByCondition(inquireContext);
    }
}
