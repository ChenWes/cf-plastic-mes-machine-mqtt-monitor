package com.cf.framework.security.handle;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cf.common.constant.MessageCode;
import com.cf.common.utils.MessageUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.cf.common.constant.HttpStatus;
import com.cf.common.core.domain.AjaxResult;
import com.cf.common.utils.ServletUtils;
import com.cf.common.utils.StringUtils;

/**
 * 认证失败处理类 返回未授权
 * 
 * @author WesChen
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable
{
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException
    {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = MessageUtils.message(MessageCode.AUTHENTICATION_ERROR);
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(code, msg)));
    }
}
