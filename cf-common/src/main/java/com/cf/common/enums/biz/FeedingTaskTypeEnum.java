package com.cf.common.enums.biz;

/**
 * 辅机类型: 烤料机（dryer）、模温机（mold_temperature_machine）
 *
 * @author coder-ren
 * @date 2026/1/8 11:08
 */
public enum FeedingTaskTypeEnum {

    /**
     * 正常烤料
     */
    NORMAL("normal", "正常烤料"),
    /**
     * 预烤料
     */
    PREPARE("prepare", "预烤料");

    private final String code;
    private final String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    FeedingTaskTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
