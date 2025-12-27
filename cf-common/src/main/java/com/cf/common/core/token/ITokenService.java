package com.cf.common.core.token;

import com.cf.common.core.domain.entity.SysUser;
import com.cf.common.enums.LangInfoType;

/**
 * @Description token服务接口
 * @Author ccw
 * @Date 2021/5/10
 */
public interface ITokenService {

    /**
     * 获取当前登录用户
     *
     * @return
     */
    SysUser getLoginUser();

    /**
     * 获取当前用户code
     *
     * @return
     */
    String getUserCode();

    /**
     * 获取当前语言
     *
     * @return
     */
    LangInfoType getCurrentLangType();

    /**
     * 获取当前用户与权限
     * @return
     */
    SysUser getLoginUserAndPermission();
}
