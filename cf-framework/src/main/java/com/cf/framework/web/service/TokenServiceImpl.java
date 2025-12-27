package com.cf.framework.web.service;


import com.cf.common.constant.HttpStatus;
import com.cf.common.constant.MessageCode;
import com.cf.common.core.domain.entity.SysUser;
import com.cf.common.core.token.ITokenService;
import com.cf.common.enums.LangInfoType;
import com.cf.common.exception.CustomException;
import com.cf.common.utils.MessageUtils;
import com.cf.common.utils.SecurityUtils;
import com.cf.system.service.ISysUserService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Locale;


/**
 * token验证处理
 *
 * @author WesChen
 */
@Component
public class TokenServiceImpl implements ITokenService {

    protected final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private ISysUserService userService;

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

    @Override
    public LangInfoType getCurrentLangType() {
        Locale locale = this.getLocale();
        //泰语
        Locale thLocale = new Locale("th", "TH");

        LangInfoType langInfoType = LangInfoType.en_US;
        if (locale.equals(Locale.SIMPLIFIED_CHINESE)) {
            langInfoType = LangInfoType.zh_CN;
        } else if (locale.equals(Locale.TRADITIONAL_CHINESE)) {
            langInfoType = LangInfoType.zh_TW;
        } else if (locale.equals(thLocale)) {
            //泰语
            langInfoType = LangInfoType.th_TH;
        }
        return langInfoType;
    }

    @Override
    public String getUserCode() {
        try {
            return getLoginUserCode();
        } catch (Exception e) {
            logger.debug(e.getMessage() == null ? e.toString() : e.getMessage());
        }
        return "";
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    @Override
    public SysUser getLoginUser() {
        try {
            SysUser user = null;
            //通过token解析出用户编号
            String userCode = getLoginUserCode();
            //通过用户编号找出用户信息
            user = userService.getUserByUserCode(userCode);
            return user;
        } catch (Exception e) {
            int code = HttpStatus.UNAUTHORIZED;
            String msg = MessageUtils.message(MessageCode.AUTHENTICATION_ERROR);
            throw new CustomException(msg, code);
        }
    }

    @Override
    public SysUser getLoginUserAndPermission() {
        try {
            SysUser user = null;
            //通过token解析出用户编号
            String userCode = getLoginUserCode();
            //通过用户编号找出用户信息
            user = userService.getUserAndPermissionByCode(userCode);
            return user;
        } catch (Exception e) {
            int code = HttpStatus.UNAUTHORIZED;
            String msg = MessageUtils.message(MessageCode.AUTHENTICATION_ERROR);
            throw new CustomException(msg, code);
        }
    }
    
    /**
     * 获取登录用户的用户编码
     *
     * @return
     */
    private String getLoginUserCode() {
        //安装框架获取auth信息
        Authentication authentication = SecurityUtils.getAuthentication();
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
}
