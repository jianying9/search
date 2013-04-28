package com.spider.service;

import com.spider.AbstractSpiderTest;
import com.spider.config.ActionNames;
import com.spider.config.SourceEnum;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author aladdin
 */
public class InsertSourceSessionServiceImplJUnitTest extends AbstractSpiderTest {

    public InsertSourceSessionServiceImplJUnitTest() {
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
    //h

    @Test
    public void test() {
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>(10);
        String[] userNames = {"hr10240001@163.com", "hr10240002@163.com", "hr10240003@163.com", "hr10240004@163.com", "hr10240005@163.com", "hr10240006@163.com", "hr10240007@163.com", "hr10240008@163.com", "hr10240009@163.com", "hr10240010@163.com", "hr10240011@163.com", "hr10240012@163.com", "hr10240013@163.com", "hr10240014@163.com", "hr10240015@163.com", "hr10240016@163.com", "hr10240017@163.com", "hr10240018@163.com", "hr10240019@163.com", "hr10240020@163.com"};
        Map<String, String> parameterMap;
        for (String userName : userNames) {
            parameterMap = new HashMap<String, String>(4, 1);
            parameterMap.put("source", SourceEnum.SINA.name());
            parameterMap.put("userName", userName);
            parameterMap.put("password", "ljy1024");
            mapList.add(parameterMap);
        }
        String result;
        for (Map<String, String> map : mapList) {
            result = this.testHandler.execute(ActionNames.INSERT_SOURCE_SESSION, map);
            System.out.println(result);
        }
    }
}
