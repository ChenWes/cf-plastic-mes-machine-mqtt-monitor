package com.cf.common.core.domain.entity;


import com.cf.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 用户表 sys_User
 *
 * @author WesChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private long userId;
    private String userCode;
    private String userName;
    private String nickName;
    private String userType;
    private String email;
    private String phone;
    private String sex;
    private String avatar;
    private String defaultLang;
    private String status;
    private String remark;
    private String lastLoginIp;
    private Date lastLoginTime;

    //密码
    private String password;

    private List<SysRole> roles;
    private List<SysPermission> permissions;
    private List<SysMenu> menus;
    private Long[] roleIds;

}
