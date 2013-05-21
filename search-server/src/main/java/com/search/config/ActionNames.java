package com.search.config;

/**
 *
 * @author aladdin
 */
public class ActionNames {

    //新增一个搜索任务
    public final static String INSERT_SEARCH_TASK = "INSERT_SEARCH_TASK";
    //查询搜索任务
    public final static String INQUIRE_SEARCH_TASK = "INQUIRE_SEARCH_TASK";
    //人员搜索
    public final static String INQUIRE_EMPLOYEE = "INQUIRE_EMPLOYEE";
    //标签排名查询
    public final static String INQUIRE_TAG = "INQUIRE_TAG";
    //----------------------------------timer------------------------
    //定时更新人员的信息和关注人员
    public final static String TIMER_UPDATE_EMPLOYEE = "TIMER_UPDATE_EMPLOYEE";
    //定时更新标签统计
    public final static String TIMER_UPDATE_TAG_TOTAL = "TIMER_UPDATE_TAG_TOTAL";
    //定时任务开关
    public final static String TIMER_MANAGE = "TIMER_MANAGE";
    //-----------------------------------维护-----
    //手动解析已有人员信息的所有标签
    public final static String CONSOLE_PARSE_ALL_EMPLOYEE_TAG = "CONSOLE_PARSE_ALL_EMPLOYEE_TAG";
}
