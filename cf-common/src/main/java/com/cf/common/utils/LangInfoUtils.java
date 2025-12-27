package com.cf.common.utils;

import com.cf.common.constant.Constants;
import com.cf.common.core.domain.entity.SysLangInfo;
import com.cf.common.core.redis.RedisCache;
import com.cf.common.utils.spring.SpringUtils;
import org.apache.tomcat.util.bcel.Const;

import java.util.Collection;
import java.util.List;

/**
 * 多语言设置工具类
 *
 * @author WesChen
 */
public class LangInfoUtils {

    public static final String SEPARATOR = ",";


//    public static void setLangInfoCache(String key, List<SysLangInfo> langData) {
//        SpringUtils.getBean(RedisCache.class).setCacheObject(getCacheKey(key), langData);
//    }

    public static void setLangInfoCache(String tableName, String langKey, List<SysLangInfo> langData) {
        SpringUtils.getBean(RedisCache.class).setCacheObject(getCacheKey(tableName, langKey), langData);
    }


//    public static List<SysLangInfo> getLangInfoCache(String key) {
//        Object cacheObj = SpringUtils.getBean(RedisCache.class).getCacheObject(getCacheKey(key));
//
//        if (StringUtils.isNotNull(cacheObj)) {
//
//            List<SysLangInfo> langData = StringUtils.cast(cacheObj);
//
//            return langData;
//
//        }
//        return null;
//    }

    /**
     * 通过表名及语言类型获取数据
     * 格式例：MULTI_LANG_INFO : 语言类型 : 表名
     * @param tableName 表名
     * @param langKey 语言类型
     * @return
     */
    public static List<SysLangInfo> getLangInfoCache(String tableName, String langKey) {
        Object cacheObj = SpringUtils.getBean(RedisCache.class).getCacheObject(getCacheKey(tableName, langKey));

        if (StringUtils.isNotNull(cacheObj)) {
            List<SysLangInfo> langData = StringUtils.cast(cacheObj);
            return langData;
        }

        return null;
    }


//    public static void clearLangInfoCache() {
//        Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys(Constants.LANG_INFO_KEY + "*");
//
//        SpringUtils.getBean(RedisCache.class).deleteObject(keys);
//    }

    /**
     * 清除所有的多语言缓存数据
     * 格式例：MULTI_LANG_INFO : 语言类型 : 表名
     * 那么清除的时候，同一个表名，如果有四种语言，则会有四个key
     */
    public static void clearAllLangInfoCache() {
        Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys(Constants.LANG_INFO_WITH_TABLE_PREFIX + "*");

        SpringUtils.getBean(RedisCache.class).deleteObject(keys);
    }

    public static void clearAllLangInfoCacheByTableName(String tableName) {
        Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys(Constants.LANG_INFO_WITH_TABLE_PREFIX + "*" + tableName.toUpperCase());

        SpringUtils.getBean(RedisCache.class).deleteObject(keys);
    }

//    public static String getCacheKey(String configKey) {
//        return Constants.LANG_INFO_KEY + configKey;
//    }

    /**
     * 返回缓存多语言的key
     * 格式例：MULTI_LANG_INFO : 语言类型 : 表名
     * @param tableName 表名
     * @param langKey 语言类型
     * @return
     */
    public static String getCacheKey(String tableName, String langKey) {
        String keyString = Constants.LANG_INFO_WITH_TABLE_PREFIX + Constants.LANG_INFO_CONNECT_KEY + langKey.toUpperCase() + Constants.LANG_INFO_CONNECT_KEY + tableName.toUpperCase();
        return keyString;
    }
}
