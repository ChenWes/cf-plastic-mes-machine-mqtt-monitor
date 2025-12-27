package com.cf.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cf.common.enums.BusinessType;
import com.cf.common.enums.OperatorType;

/**
 * 自定义操作日志记录注解
 *
 * @author WesChen
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    public String title() default "";

    public String remark() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;


    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;
}
