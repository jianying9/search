package com.spider.localservice;

import com.spider.config.SourceEnum;
import com.wolf.framework.local.Local;

/**
 *
 * @author aladdin
 */
public interface HttpClientLocalService extends Local{
    
    public String get(SourceEnum sourceEnum, String url);
}
