/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.Iterator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aladdin
 */
public class JsonJUnitTest {
    
    public JsonJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void hello() {
         String text = "{\"flag\":\"SUCCESS\",\"act\":\"GET_SEARCH_TEXT\",\"data\":{\"textIdArr\":[\"SINA_福建_android_1\",\"SINA_福建_android_2\",\"SINA_福建_android_3\",\"SINA_福建_android_4\",\"SINA_福建_android_5\",\"SINA_福建_android_6\",\"SINA_福建_android_7\",\"SINA_福建_android_8\",\"SINA_福建_android_9\",\"SINA_福建_android_10\",\"SINA_福建_android_11\",\"SINA_福建_android_12\",\"SINA_福建_android_13\",\"SINA_福建_android_14\",\"SINA_福建_android_15\",\"SINA_福建_android_16\",\"SINA_福建_android_17\",\"SINA_福建_android_18\",\"SINA_福建_android_19\",\"SINA_福建_android_20\",\"SINA_福建_android_21\",\"SINA_福建_android_22\",\"SINA_福建_android_23\",\"SINA_福建_android_24\",\"SINA_福建_android_25\",\"SINA_福建_android_26\",\"SINA_福建_android_27\",\"SINA_福建_android_28\",\"SINA_福建_android_29\",\"SINA_福建_android_30\",\"SINA_福建_android_31\",\"SINA_福建_android_32\",\"SINA_福建_android_33\",\"SINA_福建_android_34\",\"SINA_福建_android_35\",\"SINA_福建_android_36\",\"SINA_福建_android_37\",\"SINA_福建_android_38\",\"SINA_福建_android_39\",\"SINA_福建_android_40\",\"SINA_福建_android_41\",\"SINA_福建_android_42\",\"SINA_福建_android_43\",\"SINA_福建_android_44\",\"SINA_福建_android_45\",\"SINA_福建_android_46\",\"SINA_福建_android_47\",\"SINA_福建_android_48\",\"SINA_福建_android_49\",\"SINA_福建_android_50\"]}}";
         ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readValue(text, JsonNode.class);
        } catch (IOException e) {
        }
        String flag = rootNode.get("flag").getTextValue();
         System.out.println(flag);
        if(flag.equals("SUCCESS")) {
            JsonNode dataNode = rootNode.get("data");
            JsonNode textIdNode = dataNode.get("textIdArr");
            JsonNode idNode;
            Iterator<JsonNode> i = textIdNode.iterator();
            while(i.hasNext()) {
                idNode = i.next();
                System.out.println(idNode.getTextValue());
            }
        }
     }
}
