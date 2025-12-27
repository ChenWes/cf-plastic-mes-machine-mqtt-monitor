package com.cf.common.annotation;

import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoKeyFieldName;
import com.cf.common.enums.LangInfoTableName;

import java.lang.annotation.*;


/**
 * 自定义多语言处理注解
 *
 * @author WesChen
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD,ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lang {

    /**
     * 要转换语言的属性名
     */
    public LangInfoColumnName[] columnNames();

    /**
     *实体的表名
     */
    public LangInfoTableName tableName() ;

    /**
     * 实体的id对应的属性名
     */
    public LangInfoKeyFieldName keyFieldName() ;


}
