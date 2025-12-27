package com.cf.common.core.i18n;

import com.cf.common.core.domain.entity.SysLangInfo;

import java.util.List;

/**
 * @Description 数据国际化
 * @Author wilfmao
 * @Date 2021/5/10
 */
public interface IDataI18nService {

    void initCache();

    /**
     * 加载国际化数据到缓存
     *
     * @param lang
     * @param tableName
     * @param columnName
     */
    void loadCache(String lang, String tableName, String columnName);

    /**
     * 批量缓存
     *
     * @param langInfos 国际化数组
     */
    void batchCache(List<SysLangInfo> langInfos);

    void cache(SysLangInfo langInfo);

    /**
     * 删除指定hash key的国际化数据
     *
     * @param lang
     * @param tableName
     * @param columnName
     */
    void deleteCache(String lang, String tableName, String columnName);

    /**
     * 删除指定记录的国际化数据
     *
     * @param lang
     * @param tableName
     * @param columnName
     * @param recordId
     */
    void deleteCache(String lang, String tableName, String columnName, String recordId);

    /**
     * 删除缓存
     *
     * @param key
     * @param hKeys
     */
    void deleteCache(String key, String... hKeys);


    /**
     * 获取记录国际化信息
     *
     * @param tableName
     * @param columnName
     * @param recordId
     * @param lang
     * @return
     */
    SysLangInfo getI18nInfo(String lang, String tableName, String columnName, String recordId);

    /**
     * 判断键是否存在
     *
     * @param lang
     * @param tableName
     * @param columnName
     * @return
     */
    boolean existCacheKey(String lang, String tableName, String columnName);

    /**
     * 数据国际化转换
     *
     * @param tableName    目标表名称
     * @param columnName   目标字段名称
     * @param keyFieldName 目标表主键
     * @param lang         语言
     * @param obj          需要进行国际化的数据对象
     */
    void translate(String tableName, String columnName, String keyFieldName, String lang, Object obj);

    /**
     * 生成 redis hash key值
     *
     * @param filterLang
     * @param filterTable
     * @param filterColumn
     * @return
     */
    List<String> generateKeys(String filterLang, String filterTable, String filterColumn);

    void repair(String lang, String tableSchema);
}
