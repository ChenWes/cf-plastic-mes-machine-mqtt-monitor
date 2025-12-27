package com.cf.system.service.impl;

import com.cf.common.utils.DateUtils;
import com.cf.system.domain.SysRolePermission;
import com.cf.system.mapper.SysRolePermissionMapper;
import com.cf.system.service.ISysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色对应的权限Service业务层处理
 *
 * @author WesChen
 */
@Service
public class SysRolePermissionServiceImpl implements ISysRolePermissionService {
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    /**
     * 查询角色对应的权限
     *
     * @param rolePermissionId 角色对应的权限ID
     * @return 角色对应的权限
     */
    @Override
    public SysRolePermission getSysRolePermissionById(Long rolePermissionId) {
        return sysRolePermissionMapper.getSysRolePermissionById(rolePermissionId);
    }

    @Override
    public List<SysRolePermission> getSysRolePermissionByRoleId(Long roleId) {
        return sysRolePermissionMapper.getSysRolePermissionByRoleId(roleId);
    }

    /**
     * 查询角色对应的权限列表
     *
     * @param sysRolePermission 角色对应的权限
     * @return 角色对应的权限
     */
    @Override
    public List<SysRolePermission> getSysRolePermissionList(SysRolePermission sysRolePermission) {
        return sysRolePermissionMapper.getSysRolePermissionList(sysRolePermission);
    }

    /**
     * 新增角色对应的权限
     *
     * @param sysRolePermission 角色对应的权限
     * @return 结果
     */
    @Override
    public int insertSysRolePermission(SysRolePermission sysRolePermission) {
        sysRolePermission.setCreateTime(DateUtils.getNowDate());
        return sysRolePermissionMapper.insertSysRolePermission(sysRolePermission);
    }

    /**
     * 修改角色对应的权限
     *
     * @param sysRolePermission 角色对应的权限
     * @return 结果
     */
    @Override
    public int updateSysRolePermission(SysRolePermission sysRolePermission) {
        sysRolePermission.setUpdateTime(DateUtils.getNowDate());
        return sysRolePermissionMapper.updateSysRolePermission(sysRolePermission);
    }

    /**
     * 批量删除角色对应的权限
     *
     * @param rolePermissionIds 需要删除的角色对应的权限ID
     * @return 结果
     */
    @Override
    public int deleteSysRolePermissionByIds(Long[] rolePermissionIds) {
        return sysRolePermissionMapper.deleteSysRolePermissionByIds(rolePermissionIds);
    }

    /**
     * 删除角色对应的权限信息
     *
     * @param rolePermissionId 角色对应的权限ID
     * @return 结果
     */
    @Override
    public int deleteSysRolePermissionById(Long rolePermissionId) {
        return sysRolePermissionMapper.deleteSysRolePermissionById(rolePermissionId);
    }

    @Override
    public int deleteSysRolePermissionByRoleId(Long roleId) {
        return sysRolePermissionMapper.deleteSysRolePermissionByRoleId(roleId);

    }

    @Override
    public int deleteSysRolePermissionByRoleIds(Long[] roleIds) {
        return sysRolePermissionMapper.deleteSysRolePermissionByRoleIds(roleIds);
    }

    @Override
    public int deleteSysRolePermissionByMenuId(Long menuId) {
        return sysRolePermissionMapper.deleteSysRolePermissionByMenuId(menuId);
    }

    @Override
    public int deleteSysRolePermissionByPermissionId(Long permissionId) {
        return sysRolePermissionMapper.deleteSysRolePermissionByPermissionId(permissionId);
    }

    @Override
    public int batchRolePermission(List<SysRolePermission> list) {
        for (SysRolePermission sysRolePermission:list) {
            sysRolePermission.setCreateTime(DateUtils.getNowDate());
        }
        return this.sysRolePermissionMapper.batchRolePermission(list);
    }

}
