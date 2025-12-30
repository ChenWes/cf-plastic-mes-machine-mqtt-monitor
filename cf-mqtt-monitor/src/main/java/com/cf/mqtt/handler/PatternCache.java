package com.cf.mqtt.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * 正则表达式缓存管理器
 *
 * @author coder-ren
 * @date 2025/2/26 21:11
 */
public class PatternCache {

    /**
     * 缓存 Topic 正则表达式的线程安全 Map
     */
    private static final Map<String, Pattern> PATTERN_CACHE = new ConcurrentHashMap<>();

    /**
     * 获取缓存中的正则表达式
     *
     * @param key Topic 的字符串表示
     * @return 对应的正则表达式，如果不存在则返回 null
     */
    public static Pattern getPattern(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }
        return PATTERN_CACHE.get(key);
    }

    /**
     * 将正则表达式放入缓存中
     *
     * @param key     Topic 的字符串表示
     * @param pattern 编译好的正则表达式
     * @return 如果缓存中已存在该键，则返回旧值；否则返回 null
     */
    public static Pattern putPattern(String key, Pattern pattern) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        }
        return PATTERN_CACHE.put(key, pattern);
    }

    /**
     * 编译并缓存正则表达式
     *
     * @param key Topic 的字符串表示
     * @return 编译后的正则表达式
     */
    public static Pattern compileAndCache(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }
        Pattern pattern = PATTERN_CACHE.computeIfAbsent(key, Pattern::compile);
        return pattern;
    }

    /**
     * 移除缓存中的正则表达式
     *
     * @param key Topic 的字符串表示
     * @return 如果缓存中存在该键，则返回对应的正则表达式；否则返回 null
     */
    public static Pattern removePattern(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }
        return PATTERN_CACHE.remove(key);
    }

    /**
     * 清空所有缓存
     */
    public static void clearCache() {
        PATTERN_CACHE.clear();
    }

    /**
     * 检查缓存中是否存在指定的 Topic
     *
     * @param key Topic 的字符串表示
     * @return 如果缓存中存在该 Topic，则返回 true；否则返回 false
     */
    public static boolean containsPattern(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }
        return PATTERN_CACHE.containsKey(key);
    }
}