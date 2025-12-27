package com.cf.system.service;

import java.util.List;
import java.util.Map;

/**
 * 国际化
 */
public interface I18nService {

    /**
     * 获取数据库中数据表的主键列名
     * @param tableSchema 数据库名
     * @param tableNames  数据表列表
     * @return
     */
    List<Map<String, String>> getPrimaryKeyNames(String tableSchema, List<String> tableNames);

    void repair(String lang, String tableSchema);
}
