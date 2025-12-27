package com.cf.common.core.domain.entity;


import com.cf.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 修改用户密码
 *
 * @author WesChen
 * 2021-12-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserForChangePassword extends BaseEntity {
    private static final long serialVersionUID = 1L;

    //用户编号
    private String userCode;
    //密码
    private String password;

}
