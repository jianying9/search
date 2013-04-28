package com.spider.httpclient;

import com.spider.config.SpiderLoggerEnum;
import com.wolf.framework.logger.LogFactory;
import java.io.IOException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;

/**
 *
 * @author aladdin
 */
public class HttpClientManager {

    private final static DefaultHttpClient httpClient = new DefaultHttpClient();

    public static String execute(HttpGet httpGet) {
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = "";
        try {
            responseBody = httpClient.execute(httpGet, responseHandler);
        } catch (IOException ex) {
            Logger logger = LogFactory.getLogger(SpiderLoggerEnum.HTTP_CLIENT);
            logger.error("httpClient get error!", ex);
        }
        return responseBody;
    }
    
    public static String execute(HttpPost httpPost) {
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = "";
        try {
            responseBody = httpClient.execute(httpPost, responseHandler);
        } catch (IOException ex) {
            Logger logger = LogFactory.getLogger(SpiderLoggerEnum.HTTP_CLIENT);
            logger.error("httpClient post error!", ex);
        }
        return responseBody;
    }
}
