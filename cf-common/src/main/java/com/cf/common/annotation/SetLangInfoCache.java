package com.cf.common.annotation;

import java.lang.annotation.*;


/**
 * @Description 自定义更新缓存注解（用于更新LangInfo后刷新缓存）
 * @Author ccw
 * @Date 2021/5/10
 */
@Target({ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SetLangInfoCache {

}
