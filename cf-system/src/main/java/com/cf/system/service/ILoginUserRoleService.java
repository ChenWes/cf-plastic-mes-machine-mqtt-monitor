package com.cf.system.service;

import com.cf.common.core.domain.entity.SysRole;

import java.util.List;

/**
 * 登录用户角色服务
 *
 * @author wilfmao
 * @date 2023/07/10
 */
public interface ILoginUserRoleService {

    /**
     * 获取用户角色列表
     *
     * @param userId 用户ID
     * @return
     */
    List<SysRole> getRoleListByUserId(Long userId);

}
