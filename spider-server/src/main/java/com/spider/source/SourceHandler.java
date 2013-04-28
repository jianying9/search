package com.spider.source;

import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
public interface SourceHandler {
    
    public String getSearchText(String location, String tag, int pageIndex);
    
    public List<String> parseSearchText(String text);
    
    public String getInfoText(String sourceId);
    
    public Map<String, String> parseInfoText(String text);
    
    public String getFollowText(String sourceId);
    
    public List<String> parseFollowText(String text);
    
    public void sendMessage(String id, String nickName, String text);
    
    public Map<String, String> getNewCookie(Map<String, String> cookieMap);
    
    public Map<String, String> getLoginCookie(String userName, String password);
}
