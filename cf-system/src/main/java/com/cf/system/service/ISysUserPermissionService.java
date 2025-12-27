package com.cf.system.service;

import com.cf.system.domain.SysUserPermission;

import java.util.List;

/**
 * 用户对应的权限Service接口
 *
 * @author WesChen
 */
public interface ISysUserPermissionService {
    /**
     * 查询用户对应的权限
     *
     * @param userPermissionId 用户对应的权限ID
     * @return 用户对应的权限
     */
    SysUserPermission getSysUserPermissionById(Long userPermissionId);

    /**
     * 查询用户对应的权限列表
     *
     * @param sysUserPermission 用户对应的权限
     * @return 用户对应的权限集合
     */
    List<SysUserPermission> getSysUserPermissionList(SysUserPermission sysUserPermission);

    /**
     * 根据用户id获取用户权限列表
     *
     * @param userId
     * @return
     */
    List<SysUserPermission> getSysUserPermissionByUserId(Long userId);

    /**
     * 新增用户对应的权限
     *
     * @param sysUserPermission 用户对应的权限
     * @return 结果
     */
    int insertSysUserPermission(SysUserPermission sysUserPermission);

    /**
     * 修改用户对应的权限
     *
     * @param sysUserPermission 用户对应的权限
     * @return 结果
     */
    int updateSysUserPermission(SysUserPermission sysUserPermission);

    /**
     * 批量删除用户对应的权限
     *
     * @param userPermissionIds 需要删除的用户对应的权限ID
     * @return 结果
     */
    int deleteSysUserPermissionByIds(Long[] userPermissionIds);

    /**
     * 删除用户对应的权限信息
     *
     * @param userPermissionId 用户对应的权限ID
     * @return 结果
     */
    int deleteSysUserPermissionById(Long userPermissionId);

    /**
     * 根据用户id删除用户权限
     *
     * @param userId
     * @return
     */
    int deleteSysUserPermissionByUserId(Long userId);

    /**
     * 根据用户id列表删除用户权限
     *
     * @param userIds
     * @return
     */
    int deleteSysUserPermissionByUserIds(Long[] userIds);

    /**
     * 根据菜单id删除用户权限
     *
     * @param menuId
     * @return
     */
    int deleteSysUserPermissionByMenuId(Long menuId);

    /**
     * 根据权限id删除用户权限
     *
     * @param permissionId
     * @return
     */
    int deleteSysUserPermissionByPermissionId(Long permissionId);

    /**
     * 批量保存用户权限
     *
     * @param userPermissions
     * @return
     */
    int batchUserPermission(List<SysUserPermission> userPermissions);

}
