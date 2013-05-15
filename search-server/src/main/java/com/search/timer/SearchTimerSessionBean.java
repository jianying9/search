package com.search.timer;

import com.search.config.ActionNames;
import com.search.config.SearchLoggerEnum;
import com.wolf.framework.logger.LogFactory;
import com.wolf.framework.timer.AbstractTimer;
import java.util.HashMap;
import java.util.Map;
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
public class SearchTimerSessionBean extends AbstractTimer implements SearchTimerSessionBeanLocal {

    private Logger logger = LogFactory.getLogger(SearchLoggerEnum.TIMER);

    @Schedule(minute = "*", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "*", dayOfWeek = "*", persistent = false)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void updateEmployee() {
        this.logger.info("timer:TIMER_UPDATE_EMPLOYEE------start");
        if (TimerState.TIMER_UPDATE_EMPLOYEE_STATE == TimerState.STATE_START) {
            Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
            String result = this.executeService(ActionNames.TIMER_UPDATE_EMPLOYEE, parameterMap);
            System.out.println(result);
        } else {
            this.logger.info("timer:TIMER_UPDATE_EMPLOYEE------state -> stop");
        }
        this.logger.info("timer:TIMER_UPDATE_EMPLOYEE------finished");
    }
}
