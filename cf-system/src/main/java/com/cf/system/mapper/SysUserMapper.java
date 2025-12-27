package com.cf.system.mapper;

import com.cf.common.core.domain.entity.SysUser;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author WesChen
 */
public interface SysUserMapper {
    /**
     * 根据条件分页查询用户列表
     *
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    List<SysUser> getUserList(SysUser sysUser);

    /**
     * 通过用户名查询用户
     *
     * @param userCode 用户编码
     * @return 用户对象信息
     */
    SysUser getUserByCode(String userCode);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    SysUser getUserById(long userId);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int insertUser(SysUser user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUser(SysUser user);


    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    int deleteUserById(long userId);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    int deleteUserByIds(Long[] userIds);


}
