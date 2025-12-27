package com.cf.system.service.impl;

import com.cf.common.core.token.ITokenService;
import com.cf.common.utils.DateUtils;
import com.cf.system.domain.SysRolePermission;
import com.cf.system.domain.SysUserPermission;
import com.cf.system.domain.UserRoleAuthorize;
import com.cf.system.service.ISysRolePermissionService;
import com.cf.system.service.ISysUserPermissionService;
import com.cf.system.service.ISysUserRoleAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户角色授权接口实现
 *
 * @author ccw
 */
@Service
public class SysUserRoleAuthorizeImpl implements ISysUserRoleAuthorize {

    @Autowired
    private ISysUserPermissionService sysUserPermissionService;
    @Autowired
    private ISysRolePermissionService sysRolePermissionService;
    @Autowired
    protected ITokenService tokenService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUserRoleAuthorize(UserRoleAuthorize userRoleAuthorize)     {
        int length=0;
        length+=batchUserAuthorize(userRoleAuthorize);
        length+=batchRoleAuthorize(userRoleAuthorize);
        return length;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUserAuthorize(UserRoleAuthorize userRoleAuthorize) {
        int length=0;
        if(userRoleAuthorize.getUserIdList()!=null&&userRoleAuthorize.getUserIdList().length>0){
            sysUserPermissionService.deleteSysUserPermissionByUserIds(userRoleAuthorize.getUserIdList());
            if(userRoleAuthorize.getPermissionIdList()!=null){
                for (Long userId : userRoleAuthorize.getUserIdList()) {

                    List<SysUserPermission> list = new ArrayList<>();
                    for (Long permissionId : userRoleAuthorize.getPermissionIdList()) {
                        SysUserPermission up = new SysUserPermission();
                        up.setUserId(userId);
                        up.setPermissionId(permissionId);
                        up.setCreateTime(DateUtils.getNowDate());
                        up.setCreateBy(tokenService.getUserCode());
                        list.add(up);
                    }
                    if (list.size() > 0) {
                        sysUserPermissionService.batchUserPermission(list);
                    }
                }
            }
            length+=userRoleAuthorize.getUserIdList().length;
        }
        return length;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchRoleAuthorize(UserRoleAuthorize userRoleAuthorize) {
        int length=0;

        if(userRoleAuthorize.getRoleIdList()!=null&&userRoleAuthorize.getRoleIdList().length>0){
            sysRolePermissionService.deleteSysRolePermissionByRoleIds(userRoleAuthorize.getRoleIdList());
            //处理权限
            if(userRoleAuthorize.getPermissionIdList()!=null){
                for (Long roleId : userRoleAuthorize.getRoleIdList()) {

                    List<SysRolePermission> list = new ArrayList<>();

                    for (Long permissionId : userRoleAuthorize.getPermissionIdList()) {
                        SysRolePermission rp = new SysRolePermission();
                        rp.setRoleId(roleId);
                        rp.setPermissionId(permissionId);
                        rp.setCreateTime(DateUtils.getNowDate());
                        rp.setCreateBy(tokenService.getUserCode());
                        list.add(rp);
                    }
                    if (list.size() > 0) {
                        sysRolePermissionService.batchRolePermission(list);
                    }
                }
            }
            length+=userRoleAuthorize.getRoleIdList().length;
        }
        return length;
    }
}
