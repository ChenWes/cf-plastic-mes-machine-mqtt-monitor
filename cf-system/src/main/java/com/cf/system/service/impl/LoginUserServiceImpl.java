package com.cf.system.service.impl;

import com.cf.common.annotation.I18nIsolate;
import com.cf.common.core.domain.entity.SysPermission;
import com.cf.common.core.domain.entity.SysRole;
import com.cf.common.core.domain.entity.SysUser;
import com.cf.system.service.ILoginUserPermissionService;
import com.cf.system.service.ILoginUserRoleService;
import com.cf.system.service.ILoginUserService;
import com.cf.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 登录用户服务
 * TODO: 后期可以引入缓存，减少对数据库的查询
 *
 * @author wilfmao
 */
@Service
public class LoginUserServiceImpl implements ILoginUserService {
    private static final Logger log = LoggerFactory.getLogger(LoginUserServiceImpl.class);

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ILoginUserRoleService roleService;
    @Autowired
    private ILoginUserPermissionService permissionService;

    /**
     * 通过用编号信息查询详情
     *
     * @param userCode 用户code
     * @return 用户对象信息
     */
    @I18nIsolate(isolate = true)
    @Override
    public SysUser getUserByCode(String userCode) {
        // 获取用户信息
        SysUser user = sysUserService.getUserByUserCode(userCode);
        if (user == null) {
            return null;
        }
        // 获取用户角色权限信息
        List<SysRole> userRoles = roleService.getRoleListByUserId(user.getUserId());
        for (SysRole role : userRoles) {
            role.setPermissions(permissionService.getPermissionByRoleId(role.getRoleId()));
        }
        user.setRoles(userRoles);
        ///user.setRoleIds(userRoles.stream().map(SysRole::getRoleId).toArray(Long[]::new));

        // 获取用户权限信息
        List<SysPermission> userPerms = permissionService.getPermissionByUserId(user.getUserId());
        user.setPermissions(userPerms);
        return user;
    }


}

