package com.cf.framework.security.handle;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import com.cf.common.core.domain.model.LoginUser;
import com.cf.common.utils.StringUtils;
import com.cf.framework.web.service.TokenServiceImpl;

/**
 * 自定义退出处理类 返回成功
 *
 * @author WesChen
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        LoginUser loginUser = null;
        if (StringUtils.isNotNull(loginUser)) {
            String userName = loginUser.getUsername();
        }
    }
}
