package com.spider.config;

import com.wolf.framework.logger.LoggerType;

/**
 *
 * @author aladdin
 */
public enum SpiderLoggerEnum implements LoggerType{
    
    HTTP_CLIENT,
    SINA;

    @Override
    public String getLoggerName() {
        return this.name();
    }
}
