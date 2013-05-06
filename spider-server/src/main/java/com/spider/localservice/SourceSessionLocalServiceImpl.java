package com.spider.localservice;

import com.spider.config.SourceEnum;
import com.spider.entity.SourceSessionEntity;
import com.wolf.framework.dao.EntityDao;
import com.wolf.framework.dao.annotation.InjectDao;
import com.wolf.framework.dao.condition.Condition;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.dao.condition.OperateTypeEnum;
import com.wolf.framework.local.LocalServiceConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@LocalServiceConfig(
        interfaceInfo = SourceSessionLocalService.class,
description = "负责操作SourceSession表")
public class SourceSessionLocalServiceImpl implements SourceSessionLocalService {

    @InjectDao(clazz = SourceSessionEntity.class)
    private EntityDao<SourceSessionEntity> sourceSessionEntityDao;

    @Override
    public void insertSourceSession(SourceEnum sourceEnum, String userName, String cookie) {
        StringBuilder sessionIdBuilder = new StringBuilder(24);
        sessionIdBuilder.append(sourceEnum.name()).append('_').append(userName);
        String sessionId = sessionIdBuilder.toString();
        Map<String, String> map = new HashMap<String, String>(4, 1);
        map.put("sessionId", sessionId);
        map.put("cookie", cookie);
        map.put("lastUpdateTime", Long.toString(System.currentTimeMillis()));
        this.sourceSessionEntityDao.insert(map);
    }

    @Override
    public void updateSourceSession(String sessionId, String cookie) {
        Map<String, String> map = new HashMap<String, String>(4, 1);
        map.put("sessionId", sessionId);
        map.put("cookie", cookie);
        map.put("lastUpdateTime", Long.toString(System.currentTimeMillis()));
        this.sourceSessionEntityDao.update(map);
    }

    @Override
    public SourceSessionEntity getRandomSourceSession(SourceEnum sourceEnum) {
        SourceSessionEntity sessionEntity;
        String source = sourceEnum.name();
        InquireContext inquireContext = new InquireContext();
        Condition condition = new Condition("sessionId", OperateTypeEnum.LIKE, source);
        inquireContext.addCondition(condition);
        List<String> sessionIdList = this.sourceSessionEntityDao.inquireKeysByCondition(inquireContext);
        if (sessionIdList.isEmpty()) {
            throw new RuntimeException("SourceSessionLocalServiceImpl error:Can not find any session in source:".concat(sourceEnum.name()));
        } else {
            String sessionId = sessionIdList.get(0);
            sessionEntity = this.sourceSessionEntityDao.inquireByKey(sessionId);
        }
        return sessionEntity;
    }

    @Override
    public List<SourceSessionEntity> getAll() {
        InquireContext inquireContext = new InquireContext();
        Condition condition = new Condition("lastUpdateTime", OperateTypeEnum.GREATER, "0");
        inquireContext.addCondition(condition);
        List<SourceSessionEntity> sessionEntityList = this.sourceSessionEntityDao.inquireByCondition(inquireContext);
        return sessionEntityList;
    }

    @Override
    public void batchUpdateSourceSession(List<Map<String, String>> updateMapList) {
        String lastUpdateTime = Long.toString(System.currentTimeMillis());
        for (Map<String, String> updateMap : updateMapList) {
            updateMap.put("lastUpdateTime", lastUpdateTime);
        }
        this.sourceSessionEntityDao.batchUpdate(updateMapList);
    }

    @Override
    public String[] parseSessionId(String sessionId) {
        int index = sessionId.indexOf("_");
        String source = sessionId.substring(0, index);
        String userName = sessionId.substring(index + 1);
        String[] result = {source, userName};
        return result;
    }
}
