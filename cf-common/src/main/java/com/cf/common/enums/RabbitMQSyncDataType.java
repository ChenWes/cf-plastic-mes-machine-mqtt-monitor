package com.cf.common.enums;

/**
 * RabbitMQ同步数据类型
 * 
 * @author WesChen
 *
 */
public enum RabbitMQSyncDataType
{
    /**
     * 工单生产
     */
    JOB_ORDER_PRODUCTION,

    /**
     * 机器状态日志
     */
    MACHINE_STATUS_LOG,

    /**
     * 首检记录
     */
    FIRST_INSPECTION,

    /**
     * 巡检和尾检记录
     */
    INSPECTION,

    /**
     * 工单暂停
     */
    JOB_ORDER_SUSPEND,


    /**
     * 员工签到签退
     */
    EMPLOYEE_LOGIN_LOG,


    /**
     * 工单QC
     */
    JOB_ORDER_QC,
}
