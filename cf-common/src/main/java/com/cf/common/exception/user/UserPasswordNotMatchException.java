package com.cf.common.exception.user;

import com.cf.common.constant.MessageCode;

/**
 * 用户密码不正确或不符合规范异常类
 * 
 * @author WesChen
 */
public class UserPasswordNotMatchException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException()
    {
        super(MessageCode.USER_PASSWORD_NOT_MATCH, null);
    }
}
