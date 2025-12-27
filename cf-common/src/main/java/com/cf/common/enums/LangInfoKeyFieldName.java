package com.cf.common.enums;

/**
 * @Description 实体的id对应的属性名
 * @Author ccw
 * @Date 2021/4/29
 */
public enum LangInfoKeyFieldName {

    /**
     * 用户Id
     */
    USER_ID_KEY_FILE_NAME("userId"),

    /**
     * 角色Id
     */
    Role_ID_KEY_FILE_NAME("roleId"),

    /**
     * 权限Id
     */
    PERMISSION_ID_KEY_FILE_NAME("permissionId"),

    /**
     * 菜单Id
     */
    MENU_ID_KEY_FILE_NAME("menuId"),

    /**
     * 字典Id
     */
    DIC_ID_KEY_FILE_NAME("dicId"),


    /**
     * 工厂ID
     */
    FACTORY_ID_KEY_FILE_NAME("factoryId"),

    /**
     * 车间ID
     */
    WORKSHOP_ID_KEY_FILE_NAME("workshopId"),

    /**
     * 部门ID
     */
    DEPT_ID_KEY_FILE_NAME("deptId"),

    /**
     * 岗位ID
     */
    POST_ID_KEY_FILE_NAME("postId"),

    /**
     * 岗位ID
     */
    GROUP_ID_KEY_FILE_NAME("groupId"),

    /**
     * 机器状态ID
     */
    MACHINE_STATUS_ID_KEY_FILE_NAME("machineStatusId"),

    /**
     * 班次ID
     */
    WORK_SHIFT_ID_KEY_FILE_NAME("workShiftId"),

    /**
     * 机器类型ID
     */
    MACHINE_TYPE_ID_KEY_FILE_NAME("machineTypeId"),

    /**
     * 工序ID
     */
    PROCESS_ID_KEY_FILE_NAME("processId"),

    /**
     * 机器ID
     */
    MACHINE_ID_KEY_FILE_NAME("machineId"),

    /**
     * 员工ID
     */
    EMPLOYEE_ID_KEY_FILE_NAME("employeeId"),

    /**
     * 疵点ID
     */
    DEFECT_ID_KEY_FILE_NAME("defectId"),

    /**
     * 模具ID
     */
    MOULD_ID_KEY_FILE_NAME("mouldId"),

    /**
     * 产品ID
     */
    PRODUCT_ID_KEY_FILE_NAME("productId"),

    /**
     * 物料ID
     */
    MATERIAL_ID_KEY_FILE_NAME("materialId"),


    /**
     * 班次ID
     */
    SHIFT_ID_KEY_FILE_NAME("shiftId"),


    /**
     * 班次组
     */
    SHIFT_GROUP_ID_KEY_FILE_NAME("shiftGroupId"),

    /**
     * 客户端权限ID
     */
    CLIENT_PERMISSION_ID_KEY_FILE_NAME("clientPermissionId"),

    PLATFORM_SYSTEM_KEY_FIELD("systemId"),
    ;


    private final String value;

    LangInfoKeyFieldName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
