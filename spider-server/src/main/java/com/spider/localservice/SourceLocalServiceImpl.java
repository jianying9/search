package com.spider.localservice;

import com.spider.config.SourceEnum;
import com.spider.entity.SourceSessionEntity;
import com.spider.source.SourceHandler;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.local.LocalServiceConfig;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@LocalServiceConfig(
        interfaceInfo = SourceLocalService.class,
description = "负责获取外部渠道的信息")
public class SourceLocalServiceImpl implements SourceLocalService {

    @InjectLocalService()
    private SinaSourceLocalService sinaSourceLocalService;
    //
    @InjectLocalService()
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
    public void init() {
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

    @Override
    public void updateAllSourceSession() {
        //获取所有source session
        List<SourceSessionEntity> sessionEntityList = this.sourceSessionLocalService.getAll();
        if (sessionEntityList.isEmpty() == false) {
            SourceEnum sourceEnum;
            SourceHandler sourceHandler;
            String cookie;
            String sessionId;
            String userName;
            String[] sessionIdInfo;
            String source;
            //更新source session
            for (SourceSessionEntity sourceSessionEntity : sessionEntityList) {
                sessionId = sourceSessionEntity.getSessionId();
                sessionIdInfo = this.sourceSessionLocalService.parseSessionId(sessionId);
                source = sessionIdInfo[0];
                sourceEnum = SourceEnum.valueOf(source);
                userName = sessionIdInfo[1];
                sourceHandler = this.getSourceHandler(sourceEnum);
                cookie = sourceHandler.getLoginCookie(userName, "ljy1024");
                this.sourceSessionLocalService.updateSourceSession(sessionId, cookie);
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
            String cookie;
            String sessionId;
            String[] sessionIdInfo;
            String source;
            String userName;
            //更新source session
            for (SourceSessionEntity sourceSessionEntity : sessionEntityList) {
                sessionId = sourceSessionEntity.getSessionId();
                sessionIdInfo = this.sourceSessionLocalService.parseSessionId(sessionId);
                source = sessionIdInfo[0];
                userName = sessionIdInfo[1];
                sourceEnum = SourceEnum.valueOf(source);
                sourceHandler = this.getSourceHandler(sourceEnum);
                if (sourceSessionEntity.getCookie().isEmpty()) {
                    cookie = sourceHandler.getLoginCookie(userName, "ljy1024");
                } else {
                    cookie = sourceHandler.getNewCookie(userName, sourceSessionEntity.getCookie());
                }
                this.sourceSessionLocalService.updateSourceSession(sessionId, cookie);
            }
        }
    }

    @Override
    public void checkSourceSession(String sessionId) {
        SourceSessionEntity sessionEntity = this.sourceSessionLocalService.inquireBySessionId(sessionId);
        if (sessionEntity != null) {
            String[] sessionIdInfo = this.sourceSessionLocalService.parseSessionId(sessionEntity.getSessionId());
            SourceEnum sourceEnum = SourceEnum.valueOf(sessionIdInfo[0]);
            SourceHandler sourceHandler = this.getSourceHandler(sourceEnum);
            String userName = sessionIdInfo[1];
            sourceHandler.getNewCookie(userName, sessionEntity.getCookie());
        }
    }
}
