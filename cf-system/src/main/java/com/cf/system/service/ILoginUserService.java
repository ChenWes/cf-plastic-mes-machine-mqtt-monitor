package com.cf.system.service;

import com.cf.common.core.domain.entity.SysUser;

/**
 * 登录用户服务
 * @author wilfmao
 * @date 2023/07/10
 */
public interface ILoginUserService {

    SysUser getUserByCode(String userCode);

}
