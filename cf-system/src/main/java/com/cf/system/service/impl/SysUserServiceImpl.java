package com.cf.system.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.cf.common.annotation.Lang;
import com.cf.common.constant.MessageCode;
import com.cf.common.core.domain.entity.*;
import com.cf.common.core.token.ITokenService;
import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoKeyFieldName;
import com.cf.common.enums.LangInfoTableName;
import com.cf.common.exception.CustomException;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.MessageUtils;
import com.cf.system.domain.SysUserPermission;
import com.cf.system.domain.SysUserRole;
import com.cf.system.mapper.SysMenuMapper;
import com.cf.system.mapper.SysUserMapper;
import com.cf.system.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户 业务层处理
 *
 * @author WesChen
 */
@Service
@Lang(keyFieldName = LangInfoKeyFieldName.USER_ID_KEY_FILE_NAME, tableName = LangInfoTableName.USER_TABLE_NAME, columnNames = {LangInfoColumnName.USER_NAME_COLUMN_NAME})
public class SysUserServiceImpl implements ISysUserService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    private static String value = "";
    private static long recordId = 0L;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysUserRoleService userRoleService;
    @Autowired
    private ITokenService tokenService;
    @Autowired
    private ISysPermissionService permissionService;
    @Autowired
    private ISysUserPermissionService userPermissionService;
    @Autowired
    private ISysMenuService menuService;
    @Autowired
    private SysMenuMapper menuMapper;


    @Autowired
    private IKeycloakHelper keycloakHelper;

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysUser> getUserList(SysUser user) {
        List<SysUser> list = userMapper.getUserList(user);
        //获取角色
        for (SysUser sysUser : list) {
            sysUser.setRoles(roleService.getRoleListByUserId(sysUser.getUserId()));
            sysUser.setRoleIds(getRoleIds(sysUser.getRoles()));
        }
        return list;
    }

    /**
     * 通过用户名查询用户
     *
     * @param userCode 用户code
     * @return 用户对象信息
     */
    @Override
    @Transactional(readOnly = true)
    public SysUser getUserByCode(String userCode) {
        SysUser user = userMapper.getUserByCode(userCode);
        if (user != null) {
            user.setRoles(this.roleService.getRolesByUserId(user.getUserId(), true));
            user.setRoleIds(getRoleIds(user.getRoles()));
            user.setPermissions(this.permissionService.getPermissionByUserId(user.getUserId()));

        }
        return user;
    }

    @Cached(name = "USER:USER_CODE:", key = "#userCode", expire = 1, timeUnit = TimeUnit.MINUTES, cacheType = CacheType.REMOTE)
    @Override
    public SysUser getUserByUserCode(String userCode) {
        return userMapper.getUserByCode(userCode);
    }

    /**
     * 通过用户名查询用户及其权限
     *
     * @param userCode 用户code
     * @return 用户对象信息
     */
    @Override
    @Transactional(readOnly = true)
    public SysUser getUserAndPermissionByCode(String userCode) {
        SysUser user = userMapper.getUserByCode(userCode);
        if (user != null) {
            List<SysRole> roles = this.roleService.getRolesByUserId(user.getUserId(), false);
            user.setRoles(roles);
            user.setRoleIds(getRoleIds(user.getRoles()));
            user.setPermissions(getPermissions(user, roles));
            user.setMenus(getMenus(user.getPermissions()));
        }
        return user;
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    @Transactional(readOnly = true)
    public SysUser getUserById(long userId, boolean haveRolesAndPermissions) {
        SysUser user = userMapper.getUserById(userId);
        if (user != null && haveRolesAndPermissions) {
            user.setRoles(roleService.getRoleListByUserId(user.getUserId()));
            user.setRoleIds(getRoleIds(user.getRoles()));
            user.setPermissions(this.permissionService.getPermissionByUserId(user.getUserId()));
        }
        return user;
    }

    @Override
    public SysUser getUserById(long userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public SysUser getUserWithDifferentLang(SysUser sysUser) {
        return sysUser;
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(SysUser user) {

        //check data
        SysUser sysuser = this.userMapper.getUserByCode(user.getUserCode());
        if (sysuser != null) {
            String msg = MessageUtils.message(MessageCode.USER_USER_CODE_EXISTS, sysuser.getUserCode());
            throw new CustomException(msg);
        }
        user.setCreateTime(DateUtils.getNowDate());
        int rows = userMapper.insertUser(user);
        if (rows > 0) {
            insertUserRole(user);
            if (user.getPermissions() != null) {
                insertUserPermission(user);
            }
            boolean checkKeycloakUser = keycloakHelper.CheckUserExist(user.getUserCode());
            if (!checkKeycloakUser) {
                /**
                 * 用户不存在于Keycloak则进行新增
                 */
                boolean checkCreateUser = keycloakHelper.AddNewUser(user);
            }

        }

        return rows;
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        // 新增用户与角色管理
        if (user.getRoleIds() != null) {
            List<SysUserRole> list = new ArrayList<>();
            for (Long roleId : user.getRoleIds()) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                ur.setCreateTime(DateUtils.getNowDate());
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleService.batchUserRole(list);
            }
        }

    }

    public void insertUserPermission(SysUser user) {
        if (user.getPermissions() != null) {
            List<SysUserPermission> list = new ArrayList<>();
            for (SysPermission permission : user.getPermissions()) {
                SysUserPermission up = new SysUserPermission();
                up.setUserId(user.getUserId());
                up.setPermissionId(permission.getPermissionId());
                up.setCreateTime(DateUtils.getNowDate());
                list.add(up);
            }
            if (list.size() > 0) {
                this.userPermissionService.batchUserPermission(list);
            }
        }

    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(SysUser user) {

        if (user.getUserId() <= 0) {
            String msg = MessageUtils.message(MessageCode.USER_USER_ID_INVALID);
            throw new CustomException(msg);
        }

        if (user.getRoleIds() != null) {
            userRoleService.deleteUserRoleByUserId(user.getUserId());
            insertUserRole(user);
        }
        if (user.getPermissions() != null) {
            userPermissionService.deleteSysUserPermissionByUserId(user.getUserId());
            insertUserPermission(user);
        }
        user.setUpdateTime(DateUtils.getNowDate());
        return userMapper.updateUser(user);
    }

    @Override
    public int updateUserPassword(SysUserForChangePassword sysUserForChangePassword) {

        keycloakHelper.ChangeUserPassword(sysUserForChangePassword.getUserCode(), sysUserForChangePassword.getPassword());

        return 1;
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserById(long userId) {
        // 删除用户与角色关联
        userRoleService.deleteUserRoleByUserId(userId);
        userPermissionService.deleteSysUserPermissionByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserByIds(Long[] userIds) {
        for (long userId : userIds) {
            userRoleService.deleteUserRoleByUserId(userId);
            userPermissionService.deleteSysUserPermissionByUserId(userId);
        }
        return userMapper.deleteUserByIds(userIds);
    }

    /***
     * 获取用户所有权限（包含用户和角色）
     * @param user
     * @return
     */
    public List<SysPermission> getPermissions(SysUser user, List<SysRole> roles) {
        //所有权限
        List<SysPermission> permissions = new ArrayList<>();
        //用户权限
        permissions.addAll(this.permissionService.getPermissionByUserId(user.getUserId()));
        //获取角色权限
        for (SysRole role : roles) {
            permissions.addAll(this.permissionService.getPermissionByRoleId(role.getRoleId()));
        }
        //去重
        LinkedHashSet<SysPermission> hashSet = new LinkedHashSet<>(permissions);
        permissions = new ArrayList<>(hashSet);
        return permissions;
    }

    /***
     * 获取所有菜单
     * @param permissions
     * @return
     */
    public List<SysMenu> getMenus(List<SysPermission> permissions) {
        List<SysMenu> menus = new ArrayList<>();
        List<SysMenu> allData = this.menuMapper.getMenuList(null);
        for (SysPermission permission : permissions) {
            SysMenu sysMenu = allData.stream().filter(p -> p.getMenuId() == permission.getMenuId()).findFirst().orElse(null);
            if (sysMenu != null) {
                menus.add(sysMenu);
                SysMenu parentMenu = allData.stream().filter(p -> p.getMenuId() == sysMenu.getParentId()).findFirst().orElse(null);
                //父菜单的id不能和子菜单id一样，父菜单的父id也不能和子菜单的id一致
                List<SysMenu> menuList = new ArrayList<>();
                menuList.add(sysMenu);
                while (parentMenu != null && menuList.indexOf(parentMenu) < 0) {
                    menuList.add(parentMenu);
                    menus.add(parentMenu);
                    SysMenu finalParentMenu = parentMenu;
                    parentMenu = allData.stream().filter(p -> p.getMenuId() == finalParentMenu.getParentId()).findFirst().orElse(null);
                }
            }
        }
        //去重
        LinkedHashSet<SysMenu> hashSet = new LinkedHashSet<>(menus);
        menus = new ArrayList<>(hashSet);
        return menus;
    }

    /**
     * 根据角色列表获取角色id数组
     *
     * @param roleList
     * @return
     */
    private Long[] getRoleIds(List<SysRole> roleList) {
        if (roleList != null) {
            Long[] roleIds = new Long[roleList.size()];
            for (SysRole role : roleList) {
                roleIds[roleList.indexOf(role)] = role.getRoleId();
            }
            return roleIds;
        } else {
            return new Long[0];
        }


    }
}

