package com.search.config;

import com.wolf.framework.logger.LoggerType;

/**
 *
 * @author aladdin
 */
public enum SearchLoggerEnum implements LoggerType{
    
    TIMER,
    SEARCH;

    @Override
    public String getLoggerName() {
        return this.name();
    }
}
