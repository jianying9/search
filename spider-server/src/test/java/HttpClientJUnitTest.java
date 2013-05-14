
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author aladdin
 */
public class HttpClientJUnitTest {

    public HttpClientJUnitTest() {
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

//    @Test
    public void searchTest() throws IOException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://s.weibo.com/weibo/java");
        httpGet.addHeader("Cookie", "SUE=es%3Db6adf12f4b8601f828eb340b2d5e11c4%26ev%3Dv1%26es2%3Db4163862a9a39cba9b0fb2a2491d75c3%26rs0%3D0OZbE%252BPuRXl1I%252B5LkwYv5%252B6o3DtTXqwzbJnPwPHnys2OAt1qjL8GLQraOs4BNL9HByDpOBlmUZ7zqiEbit5vKV%252FFvzqjVqivVcPdqp9H2OK64fHhLNGkkrZURJtS21d7gt2eANxvtKdMRPBCbLPaLrkTxwlqvD7snhZxhJdn6t4%253D%26rv%3D0; Apache=6004235453438.014.1368444546265; SINAGLOBAL=6004235453438.014.1368444546265; ULV=1368444546278:1:1:1:6004235453438.014.1368444546265:; SUS=SID-3316242410-1368444556-JA-fqy84-4553ffd258b59453b1296f86d8f48cc3; wvr=5; SinaRot_wb_r_topic=59; ALF=1371036555; NSC_wjq_xfjcp.dpn_w3.6_w4=ffffffff0941013345525d5f4f58455e445a4a423660; un=hr10240001@163.com; NSC_JO5ux3qsdo02pureo32zvveomvcu4bn=ffffffff094111d845525d5f4f58455e445a4a423660; SUP=cv%3D1%26bt%3D1368444555%26et%3D1368530955%26d%3Dc909%26i%3Df297%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D0%26st%3D0%26uid%3D3316242410%26user%3Dhr10240001%2540163.com%26ag%3D4%26name%3Dhr10240001%2540163.com%26nick%3Dhr0001%26fmp%3D%26lcp%3D; SSOLoginState=1368444556; _s_tentry=-; ");
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = httpclient.execute(httpGet, responseHandler);
        int index = responseBody.lastIndexOf("pl_weibo_feedlist");
        responseBody = responseBody.substring(index);
        index = responseBody.indexOf("</script>");
        responseBody = responseBody.substring(0, index);
        System.out.println(responseBody);
        Pattern pattern = Pattern.compile("(?:weibo_nologin_name:)(\\d*)");
        Matcher matcher = pattern.matcher(responseBody);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }

    @Test
    public void followTest() throws IOException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        List<Header> headerList = new ArrayList<Header>(10);
        Header header = new BasicHeader("Cookie", "SUE=es%3Db6adf12f4b8601f828eb340b2d5e11c4%26ev%3Dv1%26es2%3Db4163862a9a39cba9b0fb2a2491d75c3%26rs0%3D0OZbE%252BPuRXl1I%252B5LkwYv5%252B6o3DtTXqwzbJnPwPHnys2OAt1qjL8GLQraOs4BNL9HByDpOBlmUZ7zqiEbit5vKV%252FFvzqjVqivVcPdqp9H2OK64fHhLNGkkrZURJtS21d7gt2eANxvtKdMRPBCbLPaLrkTxwlqvD7snhZxhJdn6t4%253D%26rv%3D0; Apache=6004235453438.014.1368444546265; SINAGLOBAL=6004235453438.014.1368444546265; ULV=1368444546278:1:1:1:6004235453438.014.1368444546265:; SUS=SID-3316242410-1368444556-JA-fqy84-4553ffd258b59453b1296f86d8f48cc3; wvr=5; SinaRot_wb_r_topic=59; ALF=1371036555; NSC_wjq_xfjcp.dpn_w3.6_w4=ffffffff0941013345525d5f4f58455e445a4a423660; un=hr10240001@163.com; NSC_JO5ux3qsdo02pureo32zvveomvcu4bn=ffffffff094111d845525d5f4f58455e445a4a423660; SUP=cv%3D1%26bt%3D1368444555%26et%3D1368530955%26d%3Dc909%26i%3Df297%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D0%26st%3D0%26uid%3D3316242410%26user%3Dhr10240001%2540163.com%26ag%3D4%26name%3Dhr10240001%2540163.com%26nick%3Dhr0001%26fmp%3D%26lcp%3D; SSOLoginState=1368444556; _s_tentry=-; ");
        headerList.add(header);
        httpclient.getParams().setParameter(ClientPNames.DEFAULT_HEADERS, headerList);
        httpclient.getCookieStore().clear();
        HttpGet httpGet = new HttpGet("http://weibo.com/1768775252/follow?from=rel&wvr=5&loc=hisfollow");
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = httpclient.execute(httpGet, responseHandler);
        int index = responseBody.lastIndexOf("pl_relation_hisFollow");
        responseBody = responseBody.substring(index);
        index = responseBody.indexOf("</script>");
        responseBody = responseBody.substring(0, index);
        System.out.println(responseBody);
    }

//     @Test
    public void getUserTest() throws IOException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("https://api.weibo.com/2/users/show.json?uid=1904178193&access_token=2.00Qt5wqC4Wm_TCbe8bb2c0d4anLg4D");
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = httpclient.execute(httpGet, responseHandler);
        System.out.println(responseBody);
    }

//     @Test
    public void notifyUserTest() throws UnsupportedEncodingException, IOException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://weibo.com/aj/mblog/add");
        httpPost.addHeader("Host", "weibo.com");
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:16.0) Gecko/20100101 Firefox/16.0");
        httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpPost.addHeader("Accept-Language", "en-US,en;q=0.5");
        httpPost.addHeader("Accept-Encoding", "gzip, deflate");
        httpPost.addHeader("Connection", "keep-alive");
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
        httpPost.addHeader("Referer", "http://weibo.com/u/3276468310");
        httpPost.addHeader("Cookie", "UOR=,weibo.com,cwebmail.mail.163.com; SINAGLOBAL=9550768576212.523.1362458494444; ULV=1364896663640:8:3:4:3067507160874.4277.1364896663552:1364887715963; un=ljy18659199765@gmail.com; myuid=2412516600; SinaRot/u/2412516600%3Fwvr%3D5%26wvr%3D5%26lf%3Dreg=28; SinaRot/u/2412516600%3Ftopnav%3D1%26wvr%3D5=9; ALF=1365503413; wvr=5; SinaRot/u/3276468310%3Fwvr%3D5%26uut%3Dfin%26from%3Dreg=91; SinaRot/u/3276468310%3Ftopnav%3D1%26wvr%3D5=71; NSC_wjq_xfjcp.dpn_w3.6_w4=ffffffff0941137845525d5f4f58455e445a4a423660; _s_tentry=-; Apache=3067507160874.4277.1364896663552; appkey=; WB_register_version=affe6957272e430f; SUE=es%3De17bcee149a8c0bd5219c7c4985dbca7%26ev%3Dv1%26es2%3D43b7b5265f437fc16458192b02ca0434%26rs0%3DRe%252B%252ByndoYsFrvi47lapT8oiLqzAZDqZUOIQaUYW0ei9CNZhKUcZyqforcfcUBdEExe%252FB1fTdqrd37HNvboAt8uPimAfa0K%252BXA4hjg36BAU5u%252FOa7W%252F%252F8e3OAg9UKdNKKYngwYet1ojW3%252F0Bb%252BuTX7M%252BT3vlGIgo1%252BQMgv%252BIC%252FV0%253D%26rv%3D0; SUP=cv%3D1%26bt%3D1364898613%26et%3D1364985013%26d%3Dc909%26i%3Db0a7%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D0%26st%3D0%26uid%3D3276468310%26user%3Dtest10240001%2540163.com%26ag%3D4%26name%3Dtest10240001%2540163.com%26nick%3Dhr_java0001%26fmp%3D%26lcp%3D; SUS=SID-3276468310-1364898613-JA-jkerl-c521f1b66d2d345ca6917325cd378cc3; SSOLoginState=1364897266; NSC_JO5ux3qsdo02pureo32zvveomvcu4bn=ffffffff0941137d45525d5f4f58455e445a4a423660; user_active=201304021830; user_unver=39e0a0793d64dc2080bdacd509c9162e; SinaRot/u/3276468310%3Fwvr%3D5%26sudaref%3Dcwebmail.mail.163.com=28");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("_surl", ""));
        nvps.add(new BasicNameValuePair("_t", "0"));
        nvps.add(new BasicNameValuePair("hottopicid", ""));
        nvps.add(new BasicNameValuePair("location", "home"));
        nvps.add(new BasicNameValuePair("module", "stissue"));
        nvps.add(new BasicNameValuePair("pic_id", ""));
        nvps.add(new BasicNameValuePair("rank", "0"));
        nvps.add(new BasicNameValuePair("rankid", ""));
        nvps.add(new BasicNameValuePair("text", "@童东鸥 你好111222222"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = httpclient.execute(httpPost, responseHandler);
        System.out.println(responseBody);
    }

//    @Test
    public void filterTest() {
        String text = "{\"code\":\"100000\",\"msg\":\"\",\"data\":\"\t<div class=\"tab_normal clearfix\">\r\n \t\t";
        Pattern formatPattern = Pattern.compile("\\\\t|\\\\n|\\\\r|\\\\");
        String result = formatPattern.matcher(text).replaceAll("");
        System.out.println(result);
    }
}
