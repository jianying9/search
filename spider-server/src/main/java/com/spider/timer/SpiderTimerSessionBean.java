package com.spider.timer;

import com.spider.config.ActionNames;
import com.spider.config.SpiderLoggerEnum;
import com.wolf.framework.logger.LogFactory;
import com.wolf.framework.timer.AbstractTimer;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import org.slf4j.Logger;

/**
 *
 * @author aladdin
 */
@Stateless
@Startup
public class SpiderTimerSessionBean extends AbstractTimer implements SpiderTimerSessionBeanLocal {

    private Logger logger = LogFactory.getLogger(SpiderLoggerEnum.TIMER);

    @Schedule(minute = "20,21,22", second = "50", dayOfMonth = "*", month = "*", year = "*", hour = "19", dayOfWeek = "*", persistent = false)
    @Override
    public void stopHttpClient() {
        this.logger.info("timer:TIMER_STOP_HTTP_CLIENT------start");
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        String result = this.executeService(ActionNames.TIMER_STOP_HTTP_CLIENT, parameterMap);
        System.out.println(result);
        this.logger.info("timer:TIMER_STOP_HTTP_CLIENT------finished");
    }

    @Schedule(minute = "24", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "19", dayOfWeek = "*", persistent = false)
    @Override
    public void checkAllHttpClientSession() {
        this.logger.info("timer:TIMER_CHECK_ALL_SOURCE_SESSION------start");
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        String result = this.executeService(ActionNames.TIMER_CHECK_ALL_SOURCE_SESSION, parameterMap);
        System.out.println(result);
        this.logger.info("timer:TIMER_CHECK_ALL_SOURCE_SESSION------finished");
    }
}
