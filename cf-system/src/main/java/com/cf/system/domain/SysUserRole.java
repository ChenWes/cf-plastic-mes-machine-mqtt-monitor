package com.cf.system.domain;

import com.cf.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户和角色关联 sys_user_role
 *
 * @author WesChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends BaseEntity {
    private long userRoleId;
    private long userId;
    private long roleId;
    
}
