package com.cf.system.domain;

import com.cf.common.annotation.Excel;
import com.cf.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户对应的权限对象 sys_user_permission
 * 
 * @author WesChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserPermission extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户权限ID */
    private Long userPermissionId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 权限ID */
    @Excel(name = "权限ID")
    private Long permissionId;

}
