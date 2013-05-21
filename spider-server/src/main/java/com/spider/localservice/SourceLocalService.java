package com.spider.localservice;

import com.spider.config.SourceEnum;
import com.wolf.framework.local.Local;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
public interface SourceLocalService extends Local{
    
    public String getSearchText(SourceEnum sourceEnum, String location, String tag, int pageIndex);
    
    public List<String> parseSearchText(SourceEnum sourceEnum, String text);
    
    public String getInfoText(SourceEnum sourceEnum, String sourceId);
    
    public Map<String, String> parseInfoText(SourceEnum sourceEnum, String text);
    
    public String getFollowText(SourceEnum sourceEnum, String sourceId);
    
    public List<String> parseFollowText(SourceEnum sourceEnum, String text);
    
    public void updateAllSourceSession();
    
    public void checkAllSourceSession();
    
    public void checkSourceSession(String sessionId);
}
