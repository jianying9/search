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
public class TimerUpdateEmployeeServiceImplJUnitTest extends AbstractSearchTest {

    public TimerUpdateEmployeeServiceImplJUnitTest() {
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
        String result = this.testHandler.execute(ActionNames.TIMER_UPDATE_EMPLOYEE, parameterMap);
        System.out.println(result);
    }
}
