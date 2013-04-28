package com.spider.service;

import com.spider.AbstractSpiderTest;
import com.spider.config.ActionNames;
import com.wolf.framework.config.DefaultResponseFlagEnum;
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
public class ParseSearchTextServiceImplJUnitTest extends AbstractSpiderTest {

    public ParseSearchTextServiceImplJUnitTest() {
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
        Map<String, String> parameterMap = new HashMap<String, String>(4, 1);
        parameterMap.put("textId", "SINA_福建_android_1");
        parameterMap.put("source", "SINA");
        String result = this.testHandler.execute(ActionNames.PARSE_SEARCH_TEXT, parameterMap);
        System.out.println(result);
    }
}
