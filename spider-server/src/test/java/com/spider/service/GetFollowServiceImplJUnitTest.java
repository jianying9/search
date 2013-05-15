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
public class GetFollowServiceImplJUnitTest extends AbstractSpiderTest {

    public GetFollowServiceImplJUnitTest() {
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
        String[] ids = {"1921966584", "1691075164", "1880893944", "1693290333", "1433998744", "1747032341",
                        "1734528463", "1743645552", "1977498755", "1298219897", "1943288981", "1677316385", 
                        "1826480347", "1710155730", "1631244195", "1459617340", "1688484443", "1063357733", 
                        "1075247944", "1725402307"};
        String result;
        Map<String, String> parameterMap = new HashMap<String, String>(4, 1);
        parameterMap.put("source", "SINA");
        for (String id : ids) {
            parameterMap.put("sourceId", id);
            result = this.testHandler.execute(ActionNames.GET_FOLLOW, parameterMap);
            System.out.println(result);
        }
    }
}
