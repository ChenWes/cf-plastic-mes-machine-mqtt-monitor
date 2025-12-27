package com.cf.framework.security.handle;


import com.cf.common.core.domain.entity.SysPermission;
import com.cf.common.core.domain.entity.SysRole;
import com.cf.common.core.domain.entity.SysUser;
import com.cf.framework.async.AsyncSysUserService;
import com.cf.framework.strategy.UserWhiteListStrategyService;
import com.cf.system.service.ILoginUserService;
import com.cf.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description 安全认证提供配置
 * @Author ccw
 * @Date 2021/5/10
 */
@Slf4j
@Component
public class SecurityAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ILoginUserService loginUserService;

    @Autowired
    private AsyncSysUserService asyncSysUserService;

    private GrantedAuthoritiesMapper grantedAuthoritiesMapper;

    @Autowired
    private UserWhiteListStrategyService userWhiteListStrategyService;

    public void setGrantedAuthoritiesMapper(GrantedAuthoritiesMapper grantedAuthoritiesMapper) {
        this.grantedAuthoritiesMapper = grantedAuthoritiesMapper;
    }

    /**
     * 验证Authentication，建立系统使用者信息principal(token)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws RuntimeException {
        //从token中获取用户信息
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) authentication;
        AccessToken accessToken = getAccessToken((token));
        String userCode = accessToken.getPreferredUsername();
        //查询用户是否存在，若不存在则存入数据库
        SysUser user = loginUserService.getUserByCode(userCode);
        if (user == null) {
            // 满足策略后，异步新增加
            boolean matchStrategy = userWhiteListStrategyService.matchStrategy(userCode);
            if (matchStrategy) {
                String email = accessToken.getEmail();
                String userName = accessToken.getName();
                asyncSysUserService.asyncSysUser(userCode, userName, email);
            }
            // 用户表中没有用户，直接提示认证失败
            log.error("Security Authentication==> user not found in biz system!! userCode: {}", userCode);
            throw new UsernameNotFoundException("user not found in biz system!");
        }
        //根据userId查询本系统用户权限，放入token中
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (user.getPermissions() != null && !user.getPermissions().isEmpty()) {
            for (SysPermission permission : user.getPermissions()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(permission.getPermissionCode()));
            }

        }
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            List<SysRole> sysRoles = user.getRoles();
            for (SysRole role : sysRoles) {
                SimpleGrantedAuthority roleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.getRoleCode());
                if (!grantedAuthorities.contains(roleGrantedAuthority)) {
                    grantedAuthorities.add(roleGrantedAuthority);
                }
                if (role.getPermissions() != null) {
                    for (SysPermission permission : role.getPermissions()) {
                        SimpleGrantedAuthority userGrantedAuthority = new SimpleGrantedAuthority(permission.getPermissionCode());
                        if (!grantedAuthorities.contains(userGrantedAuthority)) {
                            grantedAuthorities.add(userGrantedAuthority);
                        }
                    }
                }
            }
        }


        KeycloakAuthenticationToken authenticationToken = new KeycloakAuthenticationToken(token.getAccount(), token.isInteractive(), mapAuthorities(grantedAuthorities));
        return authenticationToken;

    }

    private AccessToken getAccessToken(KeycloakAuthenticationToken principal) {
        KeycloakAuthenticationToken token = principal;
        KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) token.getPrincipal();
        KeycloakSecurityContext context = keycloakPrincipal.getKeycloakSecurityContext();
        return context.getToken();
    }

    private Collection<? extends GrantedAuthority> mapAuthorities(
            List<GrantedAuthority> authorities) {
        return grantedAuthoritiesMapper != null
                ? grantedAuthoritiesMapper.mapAuthorities(authorities)
                : authorities;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return KeycloakAuthenticationToken.class.isAssignableFrom(aClass);
    }

}
