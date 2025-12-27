package com.cf.system.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.cf.common.core.domain.entity.SysPermission;
import com.cf.system.mapper.SysPermissionMapper;
import com.cf.system.service.ILoginUserPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginUserPermissionServiceImpl implements ILoginUserPermissionService {

    @Autowired
    private SysPermissionMapper permissionMapper;

    @Cached(name = "USER_PERMISSIONS:USER:", key = "#userId", expire = 1, timeUnit = TimeUnit.MINUTES, cacheType = CacheType.REMOTE)
    @Override
    public List<SysPermission> getPermissionByUserId(Long userId) {
        return this.permissionMapper.getPermissionByUserId(userId);
    }

    @Cached(name = "ROLE_PERMISSIONS:ROLE:", key = "#roleId", expire = 1, timeUnit = TimeUnit.MINUTES, cacheType = CacheType.REMOTE)
    @Override
    public List<SysPermission> getPermissionByRoleId(Long roleId) {
        return this.permissionMapper.getPermissionByRoleId(roleId);
    }

}

