package com.cf.system.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.cf.common.core.domain.entity.SysRole;
import com.cf.system.mapper.SysRoleMapper;
import com.cf.system.service.ILoginUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginUserRoleServiceImpl implements ILoginUserRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Cached(name = "USER_ROLES:USER:", key = "#userId", expire = 1, timeUnit = TimeUnit.MINUTES, cacheType = CacheType.REMOTE)
    @Override
    public List<SysRole> getRoleListByUserId(Long userId) {
        return roleMapper.getRolesByUserId(userId);
    }

}

