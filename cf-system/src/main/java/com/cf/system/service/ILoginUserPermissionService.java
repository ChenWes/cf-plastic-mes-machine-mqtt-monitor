package com.cf.system.service;

import com.cf.common.core.domain.entity.SysPermission;

import java.util.List;

/**
 * 登录用户权限服务
 *
 * @author wilfmao
 * @date 2023/07/10
 */
public interface ILoginUserPermissionService {

    /**
     * 根据用户id获取权限列表
     *
     * @param userId
     * @return
     */
    List<SysPermission> getPermissionByUserId(Long userId);

    /**
     * 根据角色id获取权限列表
     *
     * @param roleId
     * @return
     */
    List<SysPermission> getPermissionByRoleId(Long roleId);

}
