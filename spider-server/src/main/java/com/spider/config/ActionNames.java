package com.spider.config;

/**
 *
 * @author aladdin
 */
public class ActionNames {
    
    //爬虫搜索地区、标签搜索，获取搜索结果文本
    public final static String GET_SEARCH_TEXT = "GET_SEARCH_TEXT";
    //解析搜索结果文本
    public final static String PARSE_SEARCH_TEXT = "PARSE_SEARCH_TEXT";
    //爬虫抓取个人信息文本
    public final static String GET_INFO_TEXT = "GET_INFO_TEXT";
    //解析个人信息文本
    public final static String PARSE_INFO_TEXT = "PARSE_INFO_TEXT";
    //爬虫抓取人关注信息文本
    public final static String GET_FOLLOW_TEXT = "GET_FOLLOW_TEXT";
    //解析个人关注信息文本
    public final static String PARSE_FOLLOW_TEXT = "PARSE_FOLLOW_TEXT";
    //-----------
    //
    public final static String INSERT_SOURCE_SESSION = "INSERT_SOURCE_SESSION";
    //
    public final static String UPDATE_ALL_SOURCE_SESSION = "UPDATE_ALL_SOURCE_SESSION";
    //
    public final static String CHECK_ALL_SOURCE_SESSION = "CHECK_ALL_SOURCE_SESSION";
    //
}
