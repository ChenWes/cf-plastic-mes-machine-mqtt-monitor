package com.cf.common.enums;


/**
 * @author 指导文件类型-文件类型 WGI、QUI、ParameterRequirement
 * @date 2024/1/31 10:05
 */
public enum GuidelineAttachmentTypeEnum {
    WGI("wgi"),
    QUI("qui"),
    ParameterRequirement("parameterRequirement"),
    ;

    public String getType() {
        return type;
    }

    private String type;


    GuidelineAttachmentTypeEnum(String type) {
        this.type = type;
    }

    public static GuidelineAttachmentTypeEnum getEnum(String type) {
        for (GuidelineAttachmentTypeEnum value : GuidelineAttachmentTypeEnum.values()) {
            String typ = value.getType();
            if (typ.equals(type)) {
                return value;
            }
        }
        return null;
    }

}
