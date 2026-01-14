package com.cf.common.enums.biz;

/**
 * 辅机类型: 烤料机（dryer）、模温机（mold_temperature_machine）
 *
 * @author coder-ren
 * @date 2026/1/8 11:08
 */
public enum FeedingTaskStatus {

    /**
     * 准备中
     */
    PREPARING("preparing", "准备中"),

    /**
     * 执行中
     */
    RUNNING("running", "执行中"),

    /**
     * 完成
     */
    COMPLETED("completed", "完成"),

    /**
     * 清料
     */
    CLEAR("clear", "清料"),

    /**
     * 退料
     */
    RETURNED("returned", "退料"),

    /**
     * 取消
     */
    CANCELED("canceled", "取消");

    private final String code;
    private final String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    FeedingTaskStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
