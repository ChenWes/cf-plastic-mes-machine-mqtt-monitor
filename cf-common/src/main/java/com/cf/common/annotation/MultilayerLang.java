package com.cf.common.annotation;

import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoKeyFieldName;
import com.cf.common.enums.LangInfoTableName;

import java.lang.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 自定义多语言方法注解
 * 各个属性数组长度必须一致，且一一对应
 *
 * @author ccw
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultilayerLang {

    /**
     * 列名所在位置（如：a.b,则给b赋予多语言）
     *
     * @return
     */
    public String[] paths();

    /**
     * 要转换语言的属性名
     */
    public LangInfoColumnName[] columnNames();

    /**
     * 实体的表名
     */
    public LangInfoTableName[] tableNames();

    /**
     * 实体的id对应的属性名
     */
    public LangInfoKeyFieldName[] keyFieldNames();
}
