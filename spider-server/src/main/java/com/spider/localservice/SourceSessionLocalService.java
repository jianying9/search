package com.spider.localservice;

import com.spider.config.SourceEnum;
import com.spider.entity.SourceSessionEntity;
import com.wolf.framework.local.Local;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
public interface SourceSessionLocalService extends Local{
    
    public void insertSourceSession(SourceEnum sourceEnum, String userName, String cookie);
    
    public void updateSourceSession(String sessionId, String cookie);
    
    public void batchUpdateSourceSession(List<Map<String, String>> updateMapList);
    
    public List<SourceSessionEntity> getAll();
    
    public SourceSessionEntity inquireBySessionId(String sessionId);
    
    public String[] parseSessionId(String sessionId);
}
