package com.cf.common.enums;

import lombok.Getter;

/**
 * @Description
 * @Author ccw
 * @Date 4/9/2024
 */
@Getter
public enum DefectTpyeEnum {
    MMJBL("MMJBL", "ohter"),
    ACCBF("ACCBF", "poorSize");


    private final String code;
    private final String type;

    DefectTpyeEnum(String code, String type) {
        this.code = code;
        this.type = type;
    }
}
