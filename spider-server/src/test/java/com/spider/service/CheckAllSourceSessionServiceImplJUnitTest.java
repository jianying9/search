package com.spider.service;

import com.spider.AbstractSpiderTest;
import com.spider.config.ActionNames;
import java.util.HashMap;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author aladdin
 */
public class CheckAllSourceSessionServiceImplJUnitTest extends AbstractSpiderTest {

    public CheckAllSourceSessionServiceImplJUnitTest() {
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
        String result = this.testHandler.execute(ActionNames.CHECK_ALL_SOURCE_SESSION, new HashMap<String, String>(2, 1));
        System.out.println(result);
    }
}
