package com.search.service;

import com.search.AbstractSearchTest;
import com.search.config.ActionNames;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author aladdin
 */
public class TimerManageServiceImplJUnitTest extends AbstractSearchTest {

    public TimerManageServiceImplJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    //

    @Test
    public void test() {
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("timerState", "TIMER_UPDATE_EMPLOYEE_STATE");
        parameterMap.put("option", "start");
        String result = this.testHandler.execute(ActionNames.TIMER_MANAGE, parameterMap);
        System.out.println(result);
    }
}
