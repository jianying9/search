package com.spider.service;

import com.spider.AbstractSpiderTest;
import com.spider.config.ActionNames;
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
public class CheckSourceSessionServiceImplJUnitTest extends AbstractSpiderTest {

    public CheckSourceSessionServiceImplJUnitTest() {
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
        parameterMap.put("sessionId", "SINA_hr10240001@163.com");
        String result = this.testHandler.execute(ActionNames.CHECK_SOURCE_SESSION, parameterMap);
        System.out.println(result);
    }
}
