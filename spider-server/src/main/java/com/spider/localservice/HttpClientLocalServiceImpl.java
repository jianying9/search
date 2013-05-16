package com.spider.localservice;

import com.spider.config.SourceEnum;
import com.spider.config.SpiderLoggerEnum;
import com.spider.entity.SourceSessionEntity;
import com.spider.httpclient.HttpClientManager;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.local.LocalServiceConfig;
import com.wolf.framework.logger.LogFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.slf4j.Logger;

/**
 *
 * @author aladdin
 */
@LocalServiceConfig(
        interfaceInfo = HttpClientLocalService.class,
description = "负责http client的管理")
public class HttpClientLocalServiceImpl implements HttpClientLocalService {

    private final Map<SourceEnum, HttpClientManager> clientManagerMap = new EnumMap<SourceEnum, HttpClientManager>(SourceEnum.class);
    //
    @InjectLocalService()
    private SourceSessionLocalService sourceSessionLocalService;

    @Override
    public void init() {
        List<SourceSessionEntity> sessionEntityList = this.sourceSessionLocalService.getAll();
        SourceEnum sourceEnum;
        String[] sessionIdInfo;
        PoolingClientConnectionManager cm;
        DefaultHttpClient httpClient;
        Header header;
        HttpClientManager httpClientManager;
        this.clientManagerMap.clear();
        for (SourceSessionEntity sourceSessionEntity : sessionEntityList) {
            if (sourceSessionEntity.getCookie().isEmpty() == false) {
                sessionIdInfo = this.sourceSessionLocalService.parseSessionId(sourceSessionEntity.getSessionId());
                sourceEnum = SourceEnum.valueOf(sessionIdInfo[0]);
                cm = new PoolingClientConnectionManager();
                cm.setMaxTotal(10);
                httpClient = new DefaultHttpClient(cm);
                List<Header> headerList = new ArrayList<Header>(10);
                header = new BasicHeader("Cookie", sourceSessionEntity.getCookie());
                headerList.add(header);
                httpClient.getParams().setParameter(ClientPNames.DEFAULT_HEADERS, headerList);
                httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES);
                httpClientManager = this.clientManagerMap.get(sourceEnum);
                if (httpClientManager == null) {
                    httpClientManager = new HttpClientManager();
                    this.clientManagerMap.put(sourceEnum, httpClientManager);
                }
                httpClientManager.add(httpClient);
            }
        }
    }

    @Override
    public String get(SourceEnum sourceEnum, String url) {
        String responseBody = "";
        HttpClientManager httpClientManager = this.clientManagerMap.get(sourceEnum);
        DefaultHttpClient client = httpClientManager.getClient();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        HttpGet httpGet = new HttpGet(url);
        HttpConnectionParams.setConnectionTimeout(httpGet.getParams(), 20000);
        HttpConnectionParams.setSoTimeout(httpGet.getParams(), 20000);
        try {
            responseBody = client.execute(httpGet, responseHandler);
        } catch (IOException ex) {
            Logger logger = LogFactory.getLogger(SpiderLoggerEnum.HTTP_CLIENT);
            logger.error("httpClient get error!", ex);
        }
        return responseBody;
    }
}
