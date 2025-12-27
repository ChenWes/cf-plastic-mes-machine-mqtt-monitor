package com.cf.common.enums;

/**
 * @Description 国际化数据字段枚举;
 * 字段名称与所在表名称不能为空！而且字段和表需要对应得上。若 A 表中有col1 , B 表中也有col1 那么需要定义为：
 * A_COL1_NAME("col1","A"), B_COL1_NAME("col1","B");
 * @Author ccw;wilfmao
 * @Date 2021/4/29;2023/07/04
 */
public enum LangInfoColumnName {
    /**
     * 用户名
     */
    USER_NAME_COLUMN_NAME("user_name", LangInfoTableName.USER_TABLE_NAME),

    /**
     * 角色名
     */
    ROLE_NAME_COLUMN_NAME("role_name", LangInfoTableName.ROLE_TABLE_NAME),

    /**
     * 权限名
     */
    PERMISSION_NAME_COLUMN_NAME("permission_name", LangInfoTableName.PERMISSION_TABLE_NAME),

    /**
     * 菜单名
     */
    MENU_NAME_COLUMN_NAME("menu_name", LangInfoTableName.MENU_TABLE_NAME),

    /**
     * 字典名
     */
    DIC_NAME_COLUMN_NAME("dic_name", LangInfoTableName.DIC_TABLE_NAME),

    /**
     * 工厂名
     */
    FACTORY_NAME_COLUMN_NAME("factory_name", LangInfoTableName.FACTORY_TABLE_NAME),

    /**
     * 车间名
     */
    WORKSHOP_NAME_COLUMN_NAME("workshop_name", LangInfoTableName.WORKSHOP_TABLE_NAME),

    /**
     * 机器名称
     */
    MACHINE_NAME_COLUMN_NAME("machine_name", LangInfoTableName.MACHINE_TABLE_NAME),
    /**
     * 机器类型名
     */
    MACHINE_TYPE_NAME_COLUMN_NAME("machine_type_name", LangInfoTableName.MACHINE_TYPE_TABLE_NAME),

    /**
     * 工序名
     */
    PROCESS_NAME_COLUMN_NAME("process_name", LangInfoTableName.PROCESS_TABLE_NAME),
    /**
     * 部门名
     */
    DEPT_NAME_COLUMN_NAME("dept_name", LangInfoTableName.DEPT_TABLE_NAME),

    /**
     * 组别名
     */
    GROUP_NAME_COLUMN_NAME("group_name", LangInfoTableName.GROUP_TABLE_NAME),

    /**
     * 岗位名
     */
    POST_NAME_COLUMN_NAME("post_name", LangInfoTableName.POST_TABLE_NAME),

    /**
     * 员工姓名
     */
    EMPLOYEE_NAME_COLUMN_NAME("employee_name", LangInfoTableName.EMPLOYEE_TABLE_NAME),

    /**
     * 机器状态名称
     */
    MACHINE_STATUS_NAME_COLUMN_NAME("machine_status_name", LangInfoTableName.MACHINE_STATUS_TABLE_NAME),

    /**
     * 模具名称
     */
    MOULD_NAME_COLUMN_NAME("mould_name", LangInfoTableName.MOULD_TABLE_NAME),

    /**
     * 产品名称
     */
    PRODUCT_NAME_COLUMN_NAME("product_name", LangInfoTableName.PRODUCT_TABLE_NAME),

    /**
     * 疵点名称
     */
    DEFECT_NAME_COLUMN_NAME("defect_name", LangInfoTableName.DEFECT_TABLE_NAME),

    /**
     * 班次名
     */
    WORK_SHIFT_NAME_COLUMN_NAME("work_shift_name", LangInfoTableName.WORK_SHIFT_TABLE_NAME),

    /**
     * 班次名称
     */
    SHIFT_NAME_COLUMN_NAME("shift_name", LangInfoTableName.SHIFT_TABLE_NAME),

    /**
     * 班次组名称
     */
    SHIFT_GROUP_NAME_COLUMN_NAME("shift_group_name", LangInfoTableName.SHIFT_GROUP_TABLE_NAME),

    /**
     * 物料名称
     */
    MATERIAL_NAME_COLUMN_NAME("material_name", LangInfoTableName.MATERIAL_TABLE_NAME),

    /**
     * 客户端权限名称
     */
    CLIENT_PERMISSION_NAME_COLUMN_NAME("client_permission_name", LangInfoTableName.CLIENT_PERMISSION_TABLE_NAME),

    PLATFORM_SYSTEM_TITLE_COLUMN_NAME("title", LangInfoTableName.PLATFORM_SYSTEM_TABLE),

    PLATFORM_SYSTEM_TAG_COLUMN_NAME("tag", LangInfoTableName.PLATFORM_SYSTEM_TABLE),

    PLATFORM_SYSTEM_REMARK_COLUMN_NAME("remark", LangInfoTableName.PLATFORM_SYSTEM_TABLE),

    ;

    /**
     * 字段名称
     */
    private final String value;

    /**
     * 所在表名称
     */
    private final LangInfoTableName tableName;

    LangInfoColumnName(String value, LangInfoTableName tableName) {
        this.value = value;
        this.tableName = tableName;
    }

    public String getValue() {
        return value;
    }

    public LangInfoTableName getTableName() {
        return tableName;
    }

}
