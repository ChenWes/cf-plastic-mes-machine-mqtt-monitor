package com.cf.common.utils;

import com.cf.common.core.domain.entity.SysUser;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 安全服务工具类
 *
 * @author WesChen
 */
public class SecurityUtils {


    /**
     * 获取用户账户
     **/
    public static String getUserCode() {
        //安装框架获取auth信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //通过keycloak获取信息
        KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) authentication.getPrincipal();
        //通过keycloak获取上下文
        KeycloakSecurityContext context = keycloakPrincipal.getKeycloakSecurityContext();
        //通过keycloak上下文获取访问token
        AccessToken accessToken = context.getToken();
        //通过token解析出用户编号
        String userCode = accessToken.getPreferredUsername();
        // 用户编码
        return userCode;
    }

    /**
     * 获取用户
     **/
    public static SysUser getLoginUser() {

        return null;

    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
