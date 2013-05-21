package com.spider.config;

/**
 *
 * @author aladdin
 */
public class ActionNames {
    
    //爬虫搜索地区、标签搜索，获取搜索结果文本
    public final static String GET_SEARCH = "GET_SEARCH";
    //远程获取用户信息
    public final static String GET_INFO = "GET_INFO";
    //本地获取用户信息
    public final static String GET_LOCAL_INFO = "GET_LOCAL_INFO";
    //
    public final static String GET_FOLLOW = "GET_FOLLOW";
    //
    public final static String GET_HTTP_CLIENT_STATE = "GET_HTTP_CLIENT_STATE";
    //-----------
    //
    public final static String INSERT_SOURCE_SESSION = "INSERT_SOURCE_SESSION";
    //
    public final static String UPDATE_ALL_SOURCE_SESSION = "UPDATE_ALL_SOURCE_SESSION";
    //
    public final static String CHECK_SOURCE_SESSION = "CHECK_SOURCE_SESSION";
    //
    //---------------------------------timer----------------------------
    public final static String TIMER_STOP_HTTP_CLIENT = "TIMER_STOP_HTTP_CLIENT";
    //
    public final static String TIMER_START_HTTP_CLIENT = "TIMER_START_HTTP_CLIENT";
    //
    public final static String TIMER_CHECK_ALL_SOURCE_SESSION = "TIMER_CHECK_ALL_SOURCE_SESSION";
}
