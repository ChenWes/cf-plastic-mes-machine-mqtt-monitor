package com.cf.framework.web.exception;

import com.cf.common.constant.HttpStatus;
import com.cf.common.constant.MessageCode;
import com.cf.common.core.domain.AjaxDetailResult;
import com.cf.common.core.domain.AjaxResult;
import com.cf.common.exception.BaseException;
import com.cf.common.exception.CustomDetailException;
import com.cf.common.exception.CustomException;
import com.cf.common.exception.DistributedLockException;
import com.cf.common.utils.MessageUtils;
import com.cf.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理器
 *
 * @author WesChen
 */
@RestControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 基础异常
     */
    @ExceptionHandler(BaseException.class)
    public AjaxResult baseException(BaseException e)
    {
        return AjaxResult.error(MessageUtils.message(e.getMessage()));
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(CustomException.class)
    public AjaxResult businessException(CustomException e)
    {
        if (StringUtils.isNull(e.getCode()))
        {
            return AjaxResult.error(MessageUtils.message(e.getMessage()));
        }
        return AjaxResult.error(e.getCode(), MessageUtils.message(e.getMessage()));
    }

    /**
     * 业务异常
     * 包含错误明细
     * 2021-08-19 WesChen
     * @param e
     * @return
     */
    @ExceptionHandler(CustomDetailException.class)
    public AjaxDetailResult businessException(CustomDetailException e) {
        if (StringUtils.isNull(e.getCode())) {
            return AjaxDetailResult.error(MessageUtils.message(e.getMessage()), e.getMessageList());
        }
        return AjaxDetailResult.error(e.getCode(), MessageUtils.message(e.getMessage()), e.getMessageList());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handleAuthorizationException(AccessDeniedException e)
    {
        log.error(e.getMessage());
        return AjaxResult.error(HttpStatus.FORBIDDEN, MessageUtils.message(MessageCode.AUTHORIZATION_FORBIDDEN));
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult validatedBindException(BindException e)
    {
        log.error(e.getMessage(), e);
        List<ObjectError> list=e.getAllErrors();
        StringBuilder errorMsg=new StringBuilder();
        if(list!=null)
        {
            list.forEach(item->errorMsg.append(MessageUtils.message(item.getDefaultMessage())).append("  "));
        }

        return AjaxResult.error(errorMsg.toString());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validExceptionHandler(MethodArgumentNotValidException e)
    {
        log.error(e.getMessage(), e);
        List<FieldError> list=e.getBindingResult().getFieldErrors();
        StringBuilder errorMsg=new StringBuilder();
        if(list!=null)
        {
            list.forEach(item->errorMsg.append("[").append(item.getField()).append("]:").append(MessageUtils.message(item.getDefaultMessage())).append(" "));
        }
        return AjaxResult.error(errorMsg.toString());
    }

    /**
     * 自定义分布式锁异常
     * @param e
     * @return
     */
    @ExceptionHandler(DistributedLockException.class)
    public AjaxResult distributedLockException(DistributedLockException e)
    {
        if (StringUtils.isNull(e.getCode()))
        {
            return AjaxResult.error(MessageUtils.message(e.getMessage()));
        }
        return AjaxResult.error(e.getCode(), MessageUtils.message(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e)
    {
        log.error(e.getMessage(), e);
        return AjaxResult.error(MessageUtils.message(e.getMessage()));
    }




}
