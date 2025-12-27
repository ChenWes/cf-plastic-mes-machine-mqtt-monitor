package com.cf.system.service.impl;

import com.cf.common.annotation.Lang;
import com.cf.common.constant.MessageCode;
import com.cf.common.core.domain.entity.SysPermission;
import com.cf.common.core.domain.entity.SysRole;
import com.cf.common.core.domain.entity.SysUser;
import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoKeyFieldName;
import com.cf.common.enums.LangInfoTableName;
import com.cf.common.exception.CustomException;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.MessageUtils;
import com.cf.common.utils.StringUtils;
import com.cf.system.domain.SysRolePermission;
import com.cf.system.domain.SysUserRole;
import com.cf.system.mapper.SysRoleMapper;
import com.cf.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 系统角色业务处理
 * @Author ccw
 * @Date 2021/5/10
 */
@Service
@Lang(keyFieldName = LangInfoKeyFieldName.Role_ID_KEY_FILE_NAME, tableName = LangInfoTableName.ROLE_TABLE_NAME, columnNames = {LangInfoColumnName.ROLE_NAME_COLUMN_NAME})
public class SysRoleServiceImpl implements ISysRoleService {



    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private ISysUserRoleService userRoleService ;

    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysPermissionService permissionService;
    @Autowired
    private ISysRolePermissionService rolePermissionService;

    /**
     * 根据条件分页查询角色列表
     *
     * @param role 角色信息
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysRole> getRoleList(SysRole role) {
        List<SysRole> roleList=roleMapper.getRoleList(role);
        for (SysRole sysRole:roleList) {
            sysRole.setUsers(getUsers(sysRole));
            sysRole.setUserIds(getUserIds(sysRole.getUsers()));
        }
        return roleList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysRole> getRolesByUserId(Long userId,boolean haveUsersAndPermissions) {
        List<SysRole> list = this.roleMapper.getRolesByUserId(userId);
        if(haveUsersAndPermissions){
            for (SysRole role : list) {
                role.setUsers(getUsers(role));
                role.setUserIds(getUserIds(role.getUsers()));
                role.setPermissions(this.permissionService.getPermissionByRoleId(role.getRoleId()));
            }
        }
        return list;
    }

    @Override
    public List<SysRole> getRoleListByUserId(Long userId) {
        return roleMapper.getRolesByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public SysRole getRoleById(Long roleId) {
        SysRole role = this.roleMapper.getRoleById(roleId);
        if (role != null) {
            role.setUsers(getUsers(role));
            role.setUserIds(getUserIds(role.getUsers()));
            role.setPermissions(this.permissionService.getPermissionByRoleId(role.getRoleId()));
        }
        return role;
    }

    @Override
    @Transactional(readOnly = true)
    public SysRole getRoleByCode(String roleCode) {
        SysRole role = this.roleMapper.getRoleByCode(roleCode);
        if (role != null) {
            role.setUsers(getUsers(role));
            role.setUserIds(getUserIds(role.getUsers()));
            role.setPermissions(this.permissionService.getPermissionByRoleId(role.getRoleId()));
        }
        return role;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysRole> getRolesByUserCode(String userCode) {
        List<SysRole> list = this.roleMapper.getRolesByUserCode(userCode);
        for (SysRole role : list) {
            role.setUsers(getUsers(role));
            role.setUserIds(getUserIds(role.getUsers()));
            role.setPermissions(this.permissionService.getPermissionByRoleId(role.getRoleId()));
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRole(SysRole role) {

        //check data
        if(StringUtils.isEmpty( role.getRoleCode()))
        {
            String msg = MessageUtils.message(MessageCode.ROLE_ROLE_CODE_EMPTY);
            throw new CustomException(msg);
        }

        SysRole sysrole = this.roleMapper.getRoleByCode(role.getRoleCode());
        if (sysrole != null) {
            String msg = MessageUtils.message(MessageCode.ROLE_ROLE_CODE_EXISTS, sysrole.getRoleCode());
            throw new CustomException(msg);
        }
        role.setCreateTime(DateUtils.getNowDate());
        int rows =this.roleMapper.insertRole(role);
        if(rows>0)
        {
            insertUserRole(role);
            this.insertRolePermission(role);
        }

        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRole(SysRole role) {

        if (role.getUserIds() != null) {
            userRoleService.deleteUserRoleByRoleId(role.getRoleId());
            insertUserRole(role);
        }
        if (role.getPermissions() != null) {
            this.rolePermissionService.deleteSysRolePermissionByRoleId(role.getRoleId());
            this.insertRolePermission(role);
        }
        role.setUpdateTime(DateUtils.getNowDate());
        return this.roleMapper.updateRole(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleById(Long roleId) {
        this.userRoleService.deleteUserRoleByRoleId(roleId);
        this.rolePermissionService.deleteSysRolePermissionByRoleId(roleId);
        return this.roleMapper.deleteRoleById(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleByIds(Long[] roleIds) {

        for (Long roleId : roleIds) {
            this.deleteRoleById(roleId);
        }
        return roleIds.length;
    }

    /**
     * 新增用户角色信息
     *
     * @param role 角色对象
     */
    public void insertUserRole(SysRole role) {
        // 新增用户与角色管理
        if (role.getUserIds() != null) {
            List<SysUserRole> list = new ArrayList<>();
            for (Long userId : role.getUserIds()) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(role.getRoleId());
                ur.setCreateTime(DateUtils.getNowDate());
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleService.batchUserRole(list);
            }
        }

    }
    /**
     * 获取用户列表
     *
     * @param role 角色对象
     */
    public List<SysUser> getUsers(SysRole role) {
        List<SysUserRole> userRoles=userRoleService.getUserRoleByRoleId(role.getRoleId());
        List<SysUser> userList=new ArrayList<>();
        if(userRoles!=null){
            for (SysUserRole sysUserRole:userRoles) {
                userList.add(userService.getUserById(sysUserRole.getUserId()));
            }
        }
        return userList;
    }

    /**
     * 插入角色权限
     * @param role 角色
     */
    public void insertRolePermission(SysRole role) {

        if (role.getPermissions() != null) {
            List<SysRolePermission> list = new ArrayList<>();
            for (SysPermission permission : role.getPermissions()) {
                SysRolePermission rp = new SysRolePermission();
                rp.setRoleId(role.getRoleId());
                rp.setPermissionId(permission.getPermissionId());
                list.add(rp);
            }
            if (list.size() > 0) {
                this.rolePermissionService.batchRolePermission(list);
            }
        }
    }

    /**
     * 根据用户列表获取用户id数组
     * @param sysUserList
     * @return
     */
    private Long[] getUserIds(List<SysUser> sysUserList){
        if(sysUserList!=null){
            Long[] userIds=new Long[sysUserList.size()];
            for (SysUser user: sysUserList) {
                userIds[sysUserList.indexOf(user)]=user.getUserId();
            }
            return  userIds;
        }else{
            return new Long[0];
        }

    }
}
