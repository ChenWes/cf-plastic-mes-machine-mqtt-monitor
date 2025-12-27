package com.cf.system.service.impl;

import com.cf.common.annotation.Lang;
import com.cf.common.constant.MessageCode;
import com.cf.common.core.domain.entity.SysPermission;
import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoKeyFieldName;
import com.cf.common.enums.LangInfoTableName;
import com.cf.common.exception.CustomException;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.MessageUtils;
import com.cf.common.utils.StringUtils;
import com.cf.system.mapper.SysPermissionMapper;
import com.cf.system.service.ISysPermissionService;
import com.cf.system.service.ISysRolePermissionService;
import com.cf.system.service.ISysUserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description 系统权限业务处理
 * @Author ccw
 * @Date 2021/5/10
 */
@Service
@Lang(keyFieldName = LangInfoKeyFieldName.PERMISSION_ID_KEY_FILE_NAME, tableName = LangInfoTableName.PERMISSION_TABLE_NAME, columnNames = {LangInfoColumnName.PERMISSION_NAME_COLUMN_NAME})
public class SysPermissionServiceImpl implements ISysPermissionService {


    @Autowired
    private SysPermissionMapper permissionMapper;

    @Autowired
    private ISysUserPermissionService userPermissionService;

    @Autowired
    private ISysRolePermissionService rolePermissionService;

    @Override
    public List<SysPermission> getPermissionList(SysPermission permission) {
        return this.permissionMapper.getPermissionList(permission);
    }

    @Override
    public List<SysPermission> getPermissionByUserId(Long userId) {
        return this.permissionMapper.getPermissionByUserId(userId);
    }

    @Override
    public List<SysPermission> getPermissionByMenuId(Long menuId) {
        return this.permissionMapper.getPermissionByMenuId(menuId);
    }

    @Override
    public List<SysPermission> getPermissionByRoleId(Long roleId) {
        return this.permissionMapper.getPermissionByRoleId(roleId);
    }

    @Override
    public SysPermission getPermissionById(long permissionId) {
        return this.permissionMapper.getPermissionById(permissionId);
    }

    @Override
    public SysPermission getPermissionByCode(String permissionCode) {
        return this.permissionMapper.getPermissionByCode(permissionCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePermission(SysPermission permission) {
        permission.setUpdateTime(DateUtils.getNowDate());
        return this.permissionMapper.updatePermission(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertPermission(SysPermission permission) {

        //check data
        if (StringUtils.isEmpty(permission.getPermissionCode())) {
            String msg = MessageUtils.message(MessageCode.PERMISSION_PERMISSION_CODE_EMPTY);
            throw new CustomException(msg);
        }
        SysPermission sysPermission = this.permissionMapper.getPermissionByCode(permission.getPermissionCode());
        if (sysPermission != null) {
            String msg = MessageUtils.message(MessageCode.PERMISSION_PERMISSION_CODE_EXISTS, sysPermission.getPermissionCode());
            throw new CustomException(msg);

        }
        permission.setCreateTime(DateUtils.getNowDate());

        return this.permissionMapper.insertPermission(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletePermissionById(Long permissionId) {
        this.userPermissionService.deleteSysUserPermissionByPermissionId(permissionId);
        this.rolePermissionService.deleteSysRolePermissionByPermissionId(permissionId);

        return this.permissionMapper.deletePermissionById(permissionId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletePermissionByMenuId(Long menuId) {
        List<SysPermission> permissions = this.getPermissionByMenuId(menuId);
        for (SysPermission permission : permissions) {
            this.userPermissionService.deleteSysUserPermissionByPermissionId(permission.getPermissionId());
            this.rolePermissionService.deleteSysRolePermissionByPermissionId(permission.getPermissionId());
        }
        return permissionMapper.deletePermissionByMenuId(menuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletePermissionByIds(Long[] permissionIds) {
        for (Long permissionId : permissionIds) {
            this.userPermissionService.deleteSysUserPermissionByPermissionId(permissionId);
            this.rolePermissionService.deleteSysRolePermissionByPermissionId(permissionId);
        }
        return this.permissionMapper.deletePermissionByIds(permissionIds);
    }
}
