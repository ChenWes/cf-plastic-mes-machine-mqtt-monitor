package com.cf.framework.i18n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 配合@I18nIsolate、@Lang注解使用，用于国际化处理注解切面的数据传递。
 * @Author wilfmao
 * @Date 2023-06-27
 */
public class I18nIsolateContextHolder {

    public static final Logger log = LoggerFactory.getLogger(I18nIsolateContextHolder.class);

    private static final ThreadLocal<Boolean> ISOLATE_CONTEXT = new ThreadLocal<>();

    public static void setI18nIsolate(Boolean isolate) {
        ISOLATE_CONTEXT.set(isolate);
    }

    public static Boolean getI18nIsolate() {
        return ISOLATE_CONTEXT.get();
    }

    public static void clearI18nIsolate() {
        ISOLATE_CONTEXT.remove();
    }
}
