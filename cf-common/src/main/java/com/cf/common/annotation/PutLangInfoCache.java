package com.cf.common.annotation;

import java.lang.annotation.*;

/**
 * 用于添加国际化缓存, 替换@SetLangInfoCache注解
 *
 * @author wilfmao
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PutLangInfoCache {
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
     * 记录ID
     *
     * @return
     */
    String recordId() default "";

    /**
     * value值
     *
     * @return
     */
    String value() default "";

    /**
     * 是否批量操作
     *
     * @return
     */
    boolean multi() default false;
}
