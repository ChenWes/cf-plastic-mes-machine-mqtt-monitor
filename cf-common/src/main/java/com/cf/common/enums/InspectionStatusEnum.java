package com.cf.common.enums;

import lombok.Getter;

/**
 * 通用常量信息
 *
 * @author WesChen
 */

@Getter
public enum InspectionStatusEnum {
    OK(0, "OK"),
    NG(1, "NG");
    private final Integer code;
    private final String desc;

    InspectionStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
