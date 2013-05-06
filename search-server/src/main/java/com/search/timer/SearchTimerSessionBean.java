package com.search.timer;

import com.search.config.ActionNames;
import com.search.config.SearchLoggerEnum;
import com.wolf.framework.logger.LogFactory;
import com.wolf.framework.timer.AbstractTimer;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.slf4j.Logger;

/**
 *
 * @author aladdin
 */
@Stateless
@Startup
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SearchTimerSessionBean extends AbstractTimer implements SearchTimerSessionBeanLocal {

    private Logger logger = LogFactory.getLogger(SearchLoggerEnum.TIMER);

    @Schedule(minute = "0", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "22", dayOfWeek = "*")
    @Override
    public void executeSpiderTask() {
        this.logger.info("timer:EXECUTE_SPIDER_TASK------start");
        long start = System.currentTimeMillis();
        long end = start + 36000000;
        while (start < end) {
            this.logger.info("timer:EXECUTE_SPIDER_TASK------{}", start);
            this.executeService(ActionNames.EXECUTE_SPIDER_TASK, null);
            start = System.currentTimeMillis();
        }
        this.logger.info("timer:EXECUTE_SPIDER_TASK------finished");
    }
}
