package com.cf.system.service;

import com.cf.system.domain.SysRolePermission;

import java.util.List;

/**
 * 角色对应的权限Service接口
 *
 * @author WesChen
 */
public interface ISysRolePermissionService {
    /**
     * 查询角色对应的权限
     *
     * @param rolePermissionId 角色对应的权限ID
     * @return 角色对应的权限
     */
    SysRolePermission getSysRolePermissionById(Long rolePermissionId);

    /**
     * 查询角色对应的权限列表
     *
     * @param sysRolePermission 角色对应的权限
     * @return 角色对应的权限集合
     */
    List<SysRolePermission> getSysRolePermissionList(SysRolePermission sysRolePermission);

    /**
     * 根据角色id查询角色权限列表
     *
     * @param roleId
     * @return
     */
    List<SysRolePermission> getSysRolePermissionByRoleId(Long roleId);

    /**
     * 新增角色对应的权限
     *
     * @param sysRolePermission 角色对应的权限
     * @return 结果
     */
    int insertSysRolePermission(SysRolePermission sysRolePermission);

    /**
     * 修改角色对应的权限
     *
     * @param sysRolePermission 角色对应的权限
     * @return 结果
     */
    int updateSysRolePermission(SysRolePermission sysRolePermission);

    /**
     * 批量删除角色对应的权限
     *
     * @param rolePermissionIds 需要删除的角色对应的权限ID
     * @return 结果
     */
    int deleteSysRolePermissionByIds(Long[] rolePermissionIds);

    /**
     * 删除角色对应的权限信息
     *
     * @param rolePermissionId 角色对应的权限ID
     * @return 结果
     */
    int deleteSysRolePermissionById(Long rolePermissionId);

    /**
     * 根据角色id删除角色权限
     *
     * @param roleId
     * @return
     */
    int deleteSysRolePermissionByRoleId(Long roleId);

    /**
     * 根据角色id列表删除角色权限
     *
     * @param roleIds
     * @return
     */
    int deleteSysRolePermissionByRoleIds(Long[] roleIds);

    /**
     * 根据菜单id删除角色权限
     *
     * @param menuId
     * @return
     */
    int deleteSysRolePermissionByMenuId(Long menuId);

    /**
     * 根据权限id删除权限
     *
     * @param permissionId
     * @return
     */
    int deleteSysRolePermissionByPermissionId(Long permissionId);

    /**
     * 批量添加角色权限
     *
     * @param list
     * @return
     */
    int batchRolePermission(List<SysRolePermission> list);
}
