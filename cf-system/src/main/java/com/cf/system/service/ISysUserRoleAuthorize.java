package com.cf.system.service;

import com.cf.system.domain.UserRoleAuthorize;

/**
 * 用户角色授权
 *
 * @author ccw
 */
public interface ISysUserRoleAuthorize {
    /**
     * 用户角色授权
     * @param userRoleAuthorize
     * @return
     */
    int batchUserRoleAuthorize(UserRoleAuthorize userRoleAuthorize);

    /**
     * 用户授权
     * @param userRoleAuthorize
     * @return
     */
    int batchUserAuthorize(UserRoleAuthorize userRoleAuthorize);

    /**
     * 角色授权
     * @param userRoleAuthorize
     * @return
     */
    int batchRoleAuthorize(UserRoleAuthorize userRoleAuthorize);
}
