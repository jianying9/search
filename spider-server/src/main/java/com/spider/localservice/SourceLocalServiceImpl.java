package com.spider.localservice;

import com.spider.config.SourceEnum;
import com.spider.entity.SourceSessionEntity;
import com.spider.source.SourceHandler;
import com.wolf.framework.local.LocalService;
import com.wolf.framework.local.LocalServiceConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author aladdin
 */
@LocalServiceConfig(
        interfaceInfo = SourceLocalService.class,
description = "负责获取外部渠道的信息")
public class SourceLocalServiceImpl implements SourceLocalService {

    @LocalService()
    private SinaSourceLocalService sinaSourceLocalService;
    //
    @LocalService()
    private SourceSessionLocalService sourceSessionLocalService;

    private SourceHandler getSourceHandler(SourceEnum sourceEnum) {
        SourceHandler sourceHandler;
        switch (sourceEnum) {
            case SINA:
                sourceHandler = this.sinaSourceLocalService;
                break;
            default:
                sourceHandler = null;
        }
        return sourceHandler;
    }

    @Override
    public String getSearchText(SourceEnum sourceEnum, String location, String tag, int pageIndex) {
        SourceHandler sourceHandler = this.getSourceHandler(sourceEnum);
        return sourceHandler.getSearchText(location, tag, pageIndex);
    }

    @Override
    public List<String> parseSearchText(SourceEnum sourceEnum, String text) {
        SourceHandler sourceHandler = this.getSourceHandler(sourceEnum);
        return sourceHandler.parseSearchText(text);
    }

    @Override
    public String getInfoText(SourceEnum sourceEnum, String sourceId) {
        SourceHandler sourceHandler = this.getSourceHandler(sourceEnum);
        return sourceHandler.getInfoText(sourceId);
    }

    @Override
    public Map<String, String> parseInfoText(SourceEnum sourceEnum, String text) {
        SourceHandler sourceHandler = this.getSourceHandler(sourceEnum);
        return sourceHandler.parseInfoText(text);
    }

    @Override
    public String getFollowText(SourceEnum sourceEnum, String sourceId) {
        SourceHandler sourceHandler = this.getSourceHandler(sourceEnum);
        return sourceHandler.getFollowText(sourceId);
    }

    @Override
    public List<String> parseFollowText(SourceEnum sourceEnum, String text) {
        SourceHandler sourceHandler = this.getSourceHandler(sourceEnum);
        return sourceHandler.parseFollowText(text);
    }

    private Map<String, String> cookieToMap(String cookies) {
        String[] cookieArr = cookies.split("; ");
        Map<String, String> cookieMap = new HashMap<String, String>(cookieArr.length, 1);
        String[] arr;
        for (String cookie : cookieArr) {
            arr = cookie.split("=");
            cookieMap.put(arr[0], arr[1]);
        }
        return cookieMap;
    }

    private String mapToCookie(Map<String, String> cookieMap) {
        StringBuilder cookieBuilder = new StringBuilder(512);
        Set<Map.Entry<String, String>> entrySet = cookieMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            cookieBuilder.append(entry.getKey()).append('=').append(entry.getValue()).append("; ");
        }
        String cookie = cookieBuilder.toString();
        return cookie;
    }

    @Override
    public void updateAllSourceSession() {
        //获取所有source session
        List<SourceSessionEntity> sessionEntityList = this.sourceSessionLocalService.getAll();
        if (sessionEntityList.isEmpty() == false) {
            SourceEnum sourceEnum;
            SourceHandler sourceHandler;
            Map<String, String> updateMap;
            Map<String, String> newCookieMap;
            String cookie;
            String sessionId;
            String userName;
            String[] sessionIdInfo;
            String source;
            List<Map<String, String>> updateMapList = new ArrayList<Map<String, String>>(sessionEntityList.size());
            //更新source session
            for (SourceSessionEntity sourceSessionEntity : sessionEntityList) {
                sessionId = sourceSessionEntity.getSessionId();
                sessionIdInfo = this.sourceSessionLocalService.parseSessionId(sessionId);
                source = sessionIdInfo[0];
                sourceEnum = SourceEnum.valueOf(source);
                userName = sessionIdInfo[1];
                sourceHandler = this.getSourceHandler(sourceEnum);
                newCookieMap = sourceHandler.getLoginCookie(userName, "ljy1024");
                cookie = this.mapToCookie(newCookieMap);
                updateMap = new HashMap<String, String>(2, 1);
                updateMap.put("sessionId", sessionId);
                updateMap.put("cookie", cookie);
                updateMapList.add(updateMap);
            }
            //保存source session
            if (updateMapList.isEmpty() == false) {
                this.sourceSessionLocalService.batchUpdateSourceSession(updateMapList);
            }
        }
    }

    @Override
    public void checkAllSourceSession() {
        //获取所有source session
        List<SourceSessionEntity> sessionEntityList = this.sourceSessionLocalService.getAll();
        if (sessionEntityList.isEmpty() == false) {
            SourceEnum sourceEnum;
            SourceHandler sourceHandler;
            Map<String, String> updateMap;
            Map<String, String> newCookieMap;
            Map<String, String> oldCookieMap;
            String cookie;
            String sessionId;
            String[] sessionIdInfo;
            String source;
            List<Map<String, String>> updateMapList = new ArrayList<Map<String, String>>(sessionEntityList.size());
            //更新source session
            for (SourceSessionEntity sourceSessionEntity : sessionEntityList) {
                sessionId = sourceSessionEntity.getSessionId();
                sessionIdInfo = this.sourceSessionLocalService.parseSessionId(sessionId);
                source = sessionIdInfo[0];
                sourceEnum = SourceEnum.valueOf(source);
                sourceHandler = this.getSourceHandler(sourceEnum);
                oldCookieMap = this.cookieToMap(sourceSessionEntity.getCookie());
                newCookieMap = sourceHandler.getNewCookie(oldCookieMap);
                cookie = this.mapToCookie(newCookieMap);
                updateMap = new HashMap<String, String>(2, 1);
                updateMap.put("sessionId", sessionId);
                updateMap.put("cookie", cookie);
                updateMapList.add(updateMap);
            }
            //保存source session
            if (updateMapList.isEmpty() == false) {
                this.sourceSessionLocalService.batchUpdateSourceSession(updateMapList);
            }
        }
    }

    @Override
    public void insertLoginSession(SourceEnum sourceEnum, String userName, String password) {
        SourceHandler sourceHandler = this.getSourceHandler(sourceEnum);
        Map<String, String> cookieMap = sourceHandler.getLoginCookie(userName, password);
        //保存cookie
        String cookie = this.mapToCookie(cookieMap);
        this.sourceSessionLocalService.insertSourceSession(sourceEnum, userName, cookie);
    }
}
