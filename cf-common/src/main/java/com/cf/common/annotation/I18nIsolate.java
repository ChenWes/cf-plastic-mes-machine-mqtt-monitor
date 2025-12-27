package com.cf.common.annotation;

import java.lang.annotation.*;

/**
 * 数据国际化隔离，当 isolate = true 时，@Lang注解对应的LangAspect中的处理方式会受到影响，
 * 不会再对数据进行国际化。 LangAspect 对 getXXX(...)方法进行拦截进行国际化操作，
 * 但业务上是并不需要对数据进行国际化操作，导致执行了多余的国际化查询SQL。影响系统性能。
 * usage:
 * @I18nIsolate(isolate=true)  // 阻断getItem的@Lang注解的国际化行为
 * public void save(Entity entity){
 *     Item item = itemService.getItem(...); // getItem方法被@Lang注解标注
 * }
 * @author wilfmao
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface I18nIsolate {
    /**
     * 是否阻断数据国际化操作
     *
     * @return
     */
    boolean isolate() default false;
}
