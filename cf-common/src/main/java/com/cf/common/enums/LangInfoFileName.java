package com.cf.common.enums;

/**
 * @Description 要转换语言的属性名
 * @Author ccw
 * @Date 2021/4/29
 */
public enum LangInfoFileName {

    /**
     * 用户名
     */
    USER_NAME_FILE_NAME("userName"),

    /**
     * 角色名
     */
    Role_NAME_FILE_NAME("roleName"),

    /**
     * 权限名
     */
    PERMISSION_NAME_FILE_NAME("permissionName"),

    /**
     * 菜单名
     */
    MENU_NAME_FILE_NAME("menuName"),

    /**
     * 字典名
     */
    DIC_NAME_FILE_NAME("dicName"),

    /**
     * 工厂名
     */
    FACTORY_NAME_FILE_NAME("factoryName"),

    /**
     * 车间名
     */
    WORKSHOP_NAME_FILE_NAME("workshopName"),

    /**
     * 部门名
     */
    DEPT_NAME_FILE_NAME("deptName"),

    /**
     * 岗位名
     */
    POST_NAME_FILE_NAME("postName"),

    /**
     * 组别名
     */
    GROUP_NAME_FILE_NAME("groupName"),

    /**
     * 机器状态名
     */
    MACHINE_STATUS_NAME_FILE_NAME("machineStatusName"),

    /**
     * 班次名
     */
    WORK_SHIFT_NAME_FILE_NAME("workShiftName"),

    /**
     * 机器类型名
     */
    MACHINE_TYPE_NAME_FILE_NAME("machineTypeName");

    private final String value;

    LangInfoFileName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
