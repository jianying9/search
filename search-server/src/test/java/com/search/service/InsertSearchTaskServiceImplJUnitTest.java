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
public class InsertSearchTaskServiceImplJUnitTest extends AbstractSearchTest {

    public InsertSearchTaskServiceImplJUnitTest() {
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
//        parameterMap.put("tag", "android");
//        parameterMap.put("tag", "iphone");
        parameterMap.put("tag", "nginx");
        parameterMap.put("location", "福建");
        parameterMap.put("source", "SINA");
        String result = this.testHandler.execute(ActionNames.INSERT_SEARCH_TASK, parameterMap);
        System.out.println(result);
    }
}
