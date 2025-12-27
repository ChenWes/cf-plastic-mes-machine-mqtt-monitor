package com.cf.system.service.impl;

import com.cf.common.utils.DateUtils;
import com.cf.system.domain.SysUserPermission;
import com.cf.system.mapper.SysUserPermissionMapper;
import com.cf.system.service.ISysUserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户对应的权限Service业务层处理
 *
 * @author WesChen
 */
@Service
public class SysUserPermissionServiceImpl implements ISysUserPermissionService {
    @Autowired
    private SysUserPermissionMapper sysUserPermissionMapper;

    /**
     * 查询用户对应的权限
     *
     * @param userPermissionId 用户对应的权限ID
     * @return 用户对应的权限
     */
    @Override
    public SysUserPermission getSysUserPermissionById(Long userPermissionId) {
        return sysUserPermissionMapper.getSysUserPermissionById(userPermissionId);
    }

    /**
     * 查询用户对应的权限列表
     *
     * @param sysUserPermission 用户对应的权限
     * @return 用户对应的权限
     */
    @Override
    public List<SysUserPermission> getSysUserPermissionList(SysUserPermission sysUserPermission) {
        return sysUserPermissionMapper.getSysUserPermissionList(sysUserPermission);
    }

    @Override
    public List<SysUserPermission> getSysUserPermissionByUserId(Long userId) {
        return sysUserPermissionMapper.getSysUserPermissionByUserId(userId);
    }

    /**
     * 新增用户对应的权限
     *
     * @param sysUserPermission 用户对应的权限
     * @return 结果
     */
    @Override
    public int insertSysUserPermission(SysUserPermission sysUserPermission) {
        sysUserPermission.setCreateTime(DateUtils.getNowDate());
        return sysUserPermissionMapper.insertSysUserPermission(sysUserPermission);
    }

    /**
     * 修改用户对应的权限
     *
     * @param sysUserPermission 用户对应的权限
     * @return 结果
     */
    @Override
    public int updateSysUserPermission(SysUserPermission sysUserPermission) {
        sysUserPermission.setUpdateTime(DateUtils.getNowDate());
        return sysUserPermissionMapper.updateSysUserPermission(sysUserPermission);
    }

    /**
     * 批量删除用户对应的权限
     *
     * @param userPermissionIds 需要删除的用户对应的权限ID
     * @return 结果
     */
    @Override
    public int deleteSysUserPermissionByIds(Long[] userPermissionIds) {
        return sysUserPermissionMapper.deleteSysUserPermissionByIds(userPermissionIds);
    }

    /**
     * 删除用户对应的权限信息
     *
     * @param userPermissionId 用户对应的权限ID
     * @return 结果
     */
    @Override
    public int deleteSysUserPermissionById(Long userPermissionId) {
        return sysUserPermissionMapper.deleteSysUserPermissionById(userPermissionId);
    }

    @Override
    public int deleteSysUserPermissionByUserId(Long userId) {
        return sysUserPermissionMapper.deleteSysUserPermissionByUserId(userId);
    }

    @Override
    public int deleteSysUserPermissionByUserIds(Long[] userIds) {
        return sysUserPermissionMapper.deleteSysUserPermissionByUserIds(userIds);
    }

    @Override
    public int deleteSysUserPermissionByMenuId(Long menuId) {
        return sysUserPermissionMapper.deleteSysUserPermissionByMenuId(menuId);
    }

    @Override
    public int deleteSysUserPermissionByPermissionId(Long permissionId) {
        return sysUserPermissionMapper.deleteSysUserPermissionByPermissionId(permissionId);
    }

    @Override
    public int batchUserPermission(List<SysUserPermission> userPermissions) {
        for (SysUserPermission sysUserPermission:userPermissions) {
            sysUserPermission.setCreateTime(DateUtils.getNowDate());
        }
        return sysUserPermissionMapper.batchUserPermission(userPermissions);
    }

}
