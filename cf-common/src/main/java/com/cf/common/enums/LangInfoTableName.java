package com.cf.common.enums;

/**
 * @Description
 * @Author ccw
 * @Date 2021/4/29
 */
public enum LangInfoTableName {
    /**
     * 用户表名
     */
    USER_TABLE_NAME("sys_user"),
    /**
     * 角色表名
     */
    ROLE_TABLE_NAME("sys_role"),

    /**
     * 权限表名
     */
    PERMISSION_TABLE_NAME("sys_permission"),
    /**
     * 菜单表名
     */
    MENU_TABLE_NAME("sys_menu"),

    /**
     * 字典表名
     */
    DIC_TABLE_NAME("sys_dic"),


    /**
     * 工厂表名
     */
    FACTORY_TABLE_NAME("factory"),

    /**
     * 车间表名
     */
    WORKSHOP_TABLE_NAME("workshop"),

    /**
     * 部门表名
     */
    DEPT_TABLE_NAME("dept"),

    /**
     * 岗位表名
     */
    POST_TABLE_NAME("post"),

    /**
     * 组别表名
     */
    GROUP_TABLE_NAME("group"),

    /**
     * 机器状态表名
     */
    MACHINE_STATUS_TABLE_NAME("machine_status"),

    /**
     * 班次表名
     */
    WORK_SHIFT_TABLE_NAME("work_shift"),

    /**
     * 机器类型表名
     */
    MACHINE_TYPE_TABLE_NAME("machine_type"),

    /**
     * 工序表名
     */
    PROCESS_TABLE_NAME("process"),

    /**
     * 机器表名
     */
    MACHINE_TABLE_NAME("machine"),

    /**
     * 员工表名
     */
    EMPLOYEE_TABLE_NAME("employee"),


    /**
     * 疵点表名
     */
    DEFECT_TABLE_NAME("defect"),


    /**
     * 模具表名
     */
    MOULD_TABLE_NAME("mould"),


    /**
     * 产品表名
     */
    PRODUCT_TABLE_NAME("product"),

    /**
     * 物料表名
     */
    MATERIAL_TABLE_NAME("material"),


    /**
     * 班次表名
     */
    SHIFT_TABLE_NAME("shift"),


    /**
     * 班次组表名
     */
    SHIFT_GROUP_TABLE_NAME("shift_group"),

    /**
     * 客户端权限表名
     */
    CLIENT_PERMISSION_TABLE_NAME("client_permission"),

    PLATFORM_SYSTEM_TABLE("platform_system"),
    ;


    private final String value;

    LangInfoTableName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
