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
import java.util.Set;

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
    public void init() {
    }

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
        int num = sessionId.indexOf("_");
        String source = sessionId.substring(0, num);
        String userName = sessionId.substring(num + 1);
        String[] result = {source, userName};
        return result;
    }

    @Override
    public Map<String, String> parseCookie(String cookies) {
        String[] cookieArr = cookies.split("; ");
        Map<String, String> cookieMap = new HashMap<String, String>(cookieArr.length, 1);
        String[] arr;
        for (String cookie : cookieArr) {
            arr = cookie.split("=");
            cookieMap.put(arr[0], arr[1]);
        }
        return cookieMap;
    }

    @Override
    public String createCookie(Map<String, String> cookieMap) {
        StringBuilder cookieBuilder = new StringBuilder(512);
        Set<Map.Entry<String, String>> entrySet = cookieMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            cookieBuilder.append(entry.getKey()).append('=').append(entry.getValue()).append("; ");
        }
        String cookie = cookieBuilder.toString();
        return cookie;
    }

    @Override
    public SourceSessionEntity inquireBySessionId(String sessionId) {
        return this.sourceSessionEntityDao.inquireByKey(sessionId);
    }
}
