package com.cf.system.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色授权对象
 *
 * @author ccw 
 */
@Data
@EqualsAndHashCode()
public class UserRoleAuthorize {
    private Long[] userIdList;
    private Long[] roleIdList;
    private Long[] permissionIdList;
}
