package com.cf.system.mapper;

import com.cf.system.domain.SysRolePermission;

import java.util.List;

/**
 * 角色对应的权限Mapper接口
 *
 * @author WesChen
 */
public interface SysRolePermissionMapper {
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
     * 根据角色id获取角色权限
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
     * 删除角色对应的权限
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
     * 批量删除角色权限
     *
     * @param roleIds
     * @return
     */
    int deleteSysRolePermissionByRoleIds(Long[] roleIds);

    /**
     * 批量删除角色对应的权限
     *
     * @param rolePermissionIds 需要删除的数据ID
     * @return 结果
     */
    int deleteSysRolePermissionByIds(Long[] rolePermissionIds);

    /**
     * 批量插入角色权限
     *
     * @param rolePermissions
     * @return
     */
    int batchRolePermission(List<SysRolePermission> rolePermissions);

    /**
     * 根据菜单id删除角色权限
     *
     * @param menuId
     * @return
     */
    int deleteSysRolePermissionByMenuId(Long menuId);

    /**
     * 根据权限id删除角色权限
     *
     * @param permissionId
     * @return
     */
    int deleteSysRolePermissionByPermissionId(Long permissionId);
}
