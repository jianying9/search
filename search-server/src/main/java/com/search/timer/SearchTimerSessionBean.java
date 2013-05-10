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

    @Schedule(minute = "0", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "22", dayOfWeek = "*", persistent = false)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void executeSpiderTask() {
        this.logger.info("timer:EXECUTE_SPIDER_TASK------start");
        long start = System.currentTimeMillis();
        long end = start + 36000000;
        int pageIndex = 1;
        int pageSize = 10;
        String result;
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        while (start < end) {
            this.logger.info("timer:EXECUTE_SPIDER_TASK------{}", start);
            parameterMap.put("pageIndex", Integer.toString(pageIndex));
            parameterMap.put("pageSize", Integer.toString(pageSize));
            result = this.executeService(ActionNames.EXECUTE_SPIDER_TASK, parameterMap);
            if (result.indexOf("SUCCESS") > -1) {
                pageIndex++;
                start = System.currentTimeMillis();
            } else {
                //结束任务
                start = end;
            }
        }
        this.logger.info("timer:EXECUTE_SPIDER_TASK------finished");
    }

    @Schedule(minute = "0", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "9", dayOfWeek = "*", persistent = false)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void executeParseTask() {
        this.logger.info("timer:EXECUTE_PARSE_TASK------start");
        long start = System.currentTimeMillis();
        long end = start + 36000000;
        int pageIndex = 1;
        int pageSize = 10;
        String result;
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        while (start < end) {
            this.logger.info("timer:EXECUTE_PARSE_TASK------{}", start);
            parameterMap.put("pageIndex", Integer.toString(pageIndex));
            parameterMap.put("pageSize", Integer.toString(pageSize));
            result = this.executeService(ActionNames.EXECUTE_PARSE_TASK, parameterMap);
            if (result.indexOf("SUCCESS") > -1) {
                pageIndex++;
                start = System.currentTimeMillis();
            } else {
                //结束任务
                start = end;
            }
        }
        this.logger.info("timer:EXECUTE_PARSE_TASK------finished");
    }

    @Override
    @Schedule(minute = "0", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "12", dayOfWeek = "*", persistent = false)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void autoInsertUpdateTask() {
        this.logger.info("timer:INSERT_UPDATE_TASK------start");
        int maxNum = 500000;
        int num = 0;
        int pageIndex = 1;
        int pageSize = 40;
        String result;
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        while (num < maxNum) {
            this.logger.info("timer:INSERT_UPDATE_TASK------{}", num);
            parameterMap.put("pageIndex", Integer.toString(pageIndex));
            parameterMap.put("pageSize", Integer.toString(pageSize));
            result = this.executeService(ActionNames.INSERT_UPDATE_TASK, parameterMap);
            if (result.indexOf("SUCCESS") > -1) {
                pageIndex++;
            } else {
                //结束任务
                num = maxNum;
            }
        }
        this.logger.info("timer:INSERT_UPDATE_TASK------finished");
    }
}
