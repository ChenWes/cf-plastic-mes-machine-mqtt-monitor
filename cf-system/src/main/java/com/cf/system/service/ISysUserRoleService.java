package com.cf.system.service;


import com.cf.system.domain.SysUserRole;

import java.util.List;

/**
 * @Description 系统用户角色服务接口
 * @Author ccw
 * @Date 2021/5/10
 */
public interface ISysUserRoleService {
    /**
     * 根据用户id删除用户角色
     *
     * @param userId
     * @return
     */
    int deleteUserRoleByUserId(Long userId);

    /**
     * 根据角色id删除用户角色
     *
     * @param roleId
     * @return
     */
    int deleteUserRoleByRoleId(Long roleId);

    /**
     * 根据角色id获取用户角色列表
     *
     * @param roleId
     * @return
     */
    List<SysUserRole> getUserRoleByRoleId(Long roleId);

    /**
     * 根据id删除用户角色
     *
     * @param userRoleId
     * @return
     */
    int deleteUserRoleById(Long userRoleId);

    /**
     * 批量保存用户角色
     *
     * @param userRoleList
     * @return
     */
    int batchUserRole(List<SysUserRole> userRoleList);

    /**
     * 根据用户id和角色id删除用户角色
     *
     * @param userRole
     * @return
     */
    int deleteUserRoleInfo(SysUserRole userRole);
}
