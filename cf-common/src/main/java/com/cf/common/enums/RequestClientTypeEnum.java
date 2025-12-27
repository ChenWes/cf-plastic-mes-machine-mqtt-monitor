package com.cf.common.enums;

/**
 * @author coder-ren
 * @date 2024/12/25 14:44
 */
public enum RequestClientTypeEnum {

    UNKNOWN("unknown", "未知类型", false),

    // 客户端
    // METAL_MES_MACHINE_CLIENT("metal_mes_machine_client", "金属MES-机边客户端", true),
    // METAL_MES_QC_CLIENT("metal_mes_qc_client", "金属MES-QC客户端", true),
    // METAL_MES_DASHBOARD_CLIENT("metal_mes_dashboard_client", "金属MES-看板端", true),
    // METAL_MES_MOBILE_CLIENT("metal_mobile_client", "金属MES-移动端", true),

    // web ui
    // METAL_MES_UI_CLIENT("metal_mes_ui_client", "金属MES-Web管理端", false),

    // 客户端
    PLASTIC_MES_MACHINE_CLIENT("plastic_mes_machine_client", "注塑MES-机边客户端", true),
    PLASTIC_MES_QC_CLIENT("plastic_mes_qc_client", "注塑MES-QC客户端", true),
    PLASTIC_MES_DASHBOARD_CLIENT("plastic_mes_dashboard_client", "注塑MES-看板端", true),
    PLASTIC_MES_MOBILE_CLIENT("plastic_mes_mobile_client", "注塑MES-移动端", true),

    // web ui
    PLASTIC_MES_UI_CLIENT("plastic_mes_ui_client", "注塑MES-Web管理端", false),

    ;


    private String code;

    private String desc;

    // 若跨系统访问时，是否允许自动创建用户
    private boolean allowGenUser;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isAllowGenUser() {
        return allowGenUser;
    }

    RequestClientTypeEnum(String code, String desc, boolean allowGenUser) {
        this.code = code;
        this.desc = desc;
        this.allowGenUser = allowGenUser;
    }

    /**
     * 获取enum
     *
     * @param code
     * @return
     */
    public static RequestClientTypeEnum getEnum(String code) {
        for (RequestClientTypeEnum typeEnum : RequestClientTypeEnum.values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum;
            }
        }
        return RequestClientTypeEnum.UNKNOWN;
    }

}
