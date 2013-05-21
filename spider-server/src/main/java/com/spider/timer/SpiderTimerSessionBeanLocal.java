package com.spider.timer;

import javax.ejb.Local;

/**
 *
 * @author aladdin
 */
@Local
public interface SpiderTimerSessionBeanLocal {
    
    public void stopHttpClient();
    
    public void checkAllHttpClientSession();
}
