package com.cf.system.domain;

import com.cf.common.annotation.Excel;
import com.cf.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色对应的权限对象 sys_role_permission
 * 
 * @author WesChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRolePermission extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 角色权限ID */
    private Long rolePermissionId;

    /** 角色ID */
    @Excel(name = "角色ID")
    private Long roleId;

    /** 权限ID */
    @Excel(name = "权限ID")
    private Long permissionId;

}
