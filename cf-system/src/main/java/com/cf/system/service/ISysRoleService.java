package com.cf.system.service;

import com.cf.common.core.domain.entity.SysRole;

import java.util.List;


/**
 * 角色业务层
 *
 * @author WesChen
 */
public interface ISysRoleService {
    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    List<SysRole> getRoleList(SysRole role);

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @param haveUsersAndPermissions
     * @return 选中角色ID列表
     */
    List<SysRole> getRolesByUserId(Long userId, boolean haveUsersAndPermissions);

    List<SysRole> getRoleListByUserId(Long userId);

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    SysRole getRoleById(Long roleId);

    /**
     * 根据code获取角色
     *
     * @param roleCode
     * @return
     */
    SysRole getRoleByCode(String roleCode);


    /**
     * 根据用户编号查询角色
     *
     * @param userCode 用户名
     * @return 角色列表
     */
    List<SysRole> getRolesByUserCode(String userCode);


    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    int insertRole(SysRole role);

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    int updateRole(SysRole role);


    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int deleteRoleById(Long roleId);

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    int deleteRoleByIds(Long[] roleIds);
}
