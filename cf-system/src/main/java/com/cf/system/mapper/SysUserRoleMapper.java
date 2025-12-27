package com.cf.system.mapper;

import com.cf.system.domain.SysUserRole;

import java.util.List;

/**
 * 用户与角色关联表 数据层
 *
 * @author WesChen
 */
public interface SysUserRoleMapper {
    /**
     * 通过用户ID删除用户和角色关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    int deleteUserRoleByUserId(Long userId);


    /**
     * 根据角色id删除用户角色关联
     *
     * @param roleId
     * @return
     */
    int deleteUserRoleByRoleId(Long roleId);

    /**
     * 根据角色id获取用户角色关联列表
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
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    int batchUserRole(List<SysUserRole> userRoleList);

    /**
     * 删除用户和角色关联信息
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    int deleteUserRoleInfo(SysUserRole userRole);
}
