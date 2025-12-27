package com.cf.common.annotation;

import java.lang.annotation.*;

/**
 * 用于清除缓存中的国际化数据
 *
 * @author wilfmao
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DeleteLangInfoCache {
    /**
     * 表名
     *
     * @return
     */
    String tableName() default "";

    /**
     * 字段名称
     *
     * @return
     */
    String columnName() default "";

    /**
     * value值
     *
     * @return
     */
    String value() default "";

    ;

    /**
     * value对应的字段
     *
     * @return
     */
    String field() default "";

    /**
     * 是否是批量
     *
     * @return
     */
    boolean multi() default false;
}
