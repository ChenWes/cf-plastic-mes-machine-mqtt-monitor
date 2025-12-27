package com.cf.common.core.domain.entity;


import com.cf.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 角色表 sys_role
 *
 * @author WesChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private long roleId;
    private String roleCode;
    private String roleName;
    private String status;

    private List<SysPermission> permissions;
    private List<SysUser> users;
    private Long[] userIds;
}
