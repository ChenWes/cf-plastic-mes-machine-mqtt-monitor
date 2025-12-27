package com.cf.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.springframework.lang.Nullable;

/**
 * 请求方式
 *
 * @author WesChen
 */
public enum HttpMethod {
    /**
     * GET方法
     */
    GET,

    /**
     * HEAD方法
     */
    HEAD,

    /**
     * POST方法
     */
    POST,

    /**
     * PUT方法
     */
    PUT,

    /**
     * PATCH方法
     */
    PATCH,

    /**
     * DELETE方法
     */
    DELETE,

    /**
     * OPTIONS方法
     */
    OPTIONS,

    /**
     * TRACE方法
     */
    TRACE;

    private static final Map<String, HttpMethod> MAPPINGS = new HashMap<>(16);

    static {
        for (HttpMethod httpMethod : values()) {
            MAPPINGS.put(httpMethod.name(), httpMethod);
        }
    }

    @Nullable
    public static HttpMethod resolve(@Nullable String method) {
        return (method != null ? MAPPINGS.get(method) : null);
    }

    public boolean matches(String method) {
        return (this == resolve(method));
    }
}
