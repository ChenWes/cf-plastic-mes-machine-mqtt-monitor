package com.cf.system.mapper;

import com.cf.common.core.domain.entity.SysPermission;

import java.util.List;

/**
 * @Description 系统权限数据接口
 * @Author ccw
 * @Date 2021/5/10
 */
public interface SysPermissionMapper {

    /**
     * 获取权限列表
     *
     * @param permission
     * @return
     */
    List<SysPermission> getPermissionList(SysPermission permission);

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

    /**
     * 根据菜单id获取权限列表
     *
     * @param menuId
     * @return
     */
    List<SysPermission> getPermissionByMenuId(Long menuId);

    /**
     * 根据id获取权限
     *
     * @param permissionId
     * @return
     */
    SysPermission getPermissionById(Long permissionId);

    /**
     * 根据code获取权限
     *
     * @param permissionCode
     * @return
     */
    SysPermission getPermissionByCode(String permissionCode);

    /**
     * 更新权限
     *
     * @param permission
     * @return
     */
    int updatePermission(SysPermission permission);

    /**
     * 插入权限
     *
     * @param permission
     * @return
     */
    int insertPermission(SysPermission permission);

    /**
     * 单个删除权限
     *
     * @param permissionId
     * @return
     */
    int deletePermissionById(Long permissionId);

    /**
     * 根据菜单id删除权限
     *
     * @param menuId
     * @return
     */
    int deletePermissionByMenuId(Long menuId);

    /**
     * 批量删除权限
     *
     * @param permissionIds
     * @return
     */
    int deletePermissionByIds(Long[] permissionIds);
}
