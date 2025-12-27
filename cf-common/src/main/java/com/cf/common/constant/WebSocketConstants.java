package com.cf.common.constant;

/**
 * 通用常量信息
 *
 * @author luorog
 */
public class WebSocketConstants {
    public static final String WEB_SOCKET_CONNECT_LINE = "_";

    /**
     * 更新机器状态，频道索引
     */
    public static final String UPDATE_MACHINE_STATUS_CHANNEL_KEY_PREFIX = "MACHINE_STATUS" + WEB_SOCKET_CONNECT_LINE;


    /**
     * 更新车间的机器状态，频道索引
     */
    public static final String UPDATE_WORKSHOP_MACHINE_STATUS_CHANNEL_KEY_PREFIX = "WORKSHOP_MACHINE_STATUS" + WEB_SOCKET_CONNECT_LINE;


    /**
     * 更新工厂的机器状态，频道索引
     */
    public static final String UPDATE_FACTORY_MACHINE_STATUS_CHANNEL_KEY_PREFIX = "FACTORY_MACHINE_STATUS" + WEB_SOCKET_CONNECT_LINE;


    /**
     * 更新单个工单状态，频道索引
     */
    public static final String UPDATE_SINGLE_JOB_ORDER_CHANNEL_KEY_PREFIX = "SINGLE_JOB_ORDER" + WEB_SOCKET_CONNECT_LINE;


    /**
     * 更新机器工单列表，频道索引
     */
    public static final String UPDATE_MACHINE_JOB_ORDER_LIST_CHANNEL_KEY_PREFIX = "MACHINE_JOB_ORDER" + WEB_SOCKET_CONNECT_LINE;


    /**
     * 更新车间工单列表，频道索引
     */
    public static final String UPDATE_WORKSHOP_JOB_ORDER_LIST_CHANNEL_KEY_PREFIX = "WORKSHOP_JOB_ORDER" + WEB_SOCKET_CONNECT_LINE;


    /**
     * 自动注册，频道索引
     */
    public static final String AUTO_REGISTER_SYNC_CHANNEL_KEY_PREFIX = "AUTO_REGISTER" + WEB_SOCKET_CONNECT_LINE;

    /**
     * 当天生日员工列表，索引频道
     */
    public static final String BIRTHDAY_EMPLOYEES = "BIRTHDAY_EMPLOYEES";

    /**
     * 首检，索引频道
     */
    public static final String FIRST_INSPECTION = "FIRST_INSPECTION" + WEB_SOCKET_CONNECT_LINE;

    /**
     * 尾检，索引频道
     */
    public static final String TAIL_INSPECTION = "TAIL_INSPECTION" + WEB_SOCKET_CONNECT_LINE;

    /**
     * 品质检查工单状态变更
     */
    public static final String INSPECTION_JOB_ORDER_STATUS_CHANGE = "INSPECTION_JOB_ORDER_STATUS_CHANGE" + WEB_SOCKET_CONNECT_LINE;

    /**
     * QC客户端通知
     */
    public static final String QC_DASHBOARD_NOTICE = "QC_DASHBOARD_NOTICE" + WEB_SOCKET_CONNECT_LINE;

}
