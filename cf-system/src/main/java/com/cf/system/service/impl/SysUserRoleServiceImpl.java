package com.cf.system.service.impl;

import com.cf.common.utils.DateUtils;
import com.cf.system.domain.SysUserRole;
import com.cf.system.mapper.SysUserRoleMapper;
import com.cf.system.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 系统用户角色业务处理
 * @Author ccw
 * @Date 2021/5/10
 */
@Service
public class SysUserRoleServiceImpl implements ISysUserRoleService {

    @Autowired
    private SysUserRoleMapper userRoleMapper;


    @Override
    public int deleteUserRoleByUserId(Long userId) {
        return userRoleMapper.deleteUserRoleByUserId(userId);
    }

    @Override
    public int deleteUserRoleByRoleId(Long roleId) {
        return userRoleMapper.deleteUserRoleByRoleId(roleId);
    }

    @Override
    public List<SysUserRole> getUserRoleByRoleId(Long roleId) {
        return userRoleMapper.getUserRoleByRoleId(roleId);
    }

    @Override
    public int deleteUserRoleById(Long userRoleId) {
        return userRoleMapper.deleteUserRoleById(userRoleId);
    }

    @Override
    public int batchUserRole(List<SysUserRole> userRoleList) {
        for(SysUserRole item:userRoleList)
        {
            item.setCreateTime(DateUtils.getNowDate());
        }
        return userRoleMapper.batchUserRole(userRoleList);
    }

    @Override
    public int deleteUserRoleInfo(SysUserRole userRole) {
        return userRoleMapper.deleteUserRoleInfo(userRole);
    }
}
