package com.spider.httpclient;

import com.spider.config.SpiderLoggerEnum;
import com.wolf.framework.logger.LogFactory;
import java.io.IOException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.slf4j.Logger;

/**
 *
 * @author aladdin
 */
public class HttpClientManager {
    
    public final static HttpClientManager MANAGER = new HttpClientManager();
    
    private final DefaultHttpClient httpClient;

    public HttpClientManager() {
        PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
        cm.setMaxTotal(100);
        this.httpClient = new DefaultHttpClient(cm);
    }

    public String execute(HttpGet httpGet) {
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = "";
        try {
            responseBody = this.httpClient.execute(httpGet, responseHandler);
        } catch (IOException ex) {
            Logger logger = LogFactory.getLogger(SpiderLoggerEnum.HTTP_CLIENT);
            logger.error("httpClient get error!", ex);
        }
        return responseBody;
    }
    
    public String execute(HttpPost httpPost) {
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = "";
        try {
            responseBody = this.httpClient.execute(httpPost, responseHandler);
        } catch (IOException ex) {
            Logger logger = LogFactory.getLogger(SpiderLoggerEnum.HTTP_CLIENT);
            logger.error("httpClient post error!", ex);
        }
        return responseBody;
    }
}
