package com.cf.common.core.lang;

/**
 * @Description 多语言业务公共接口
 * @Author ccw
 * @Date 2021/5/14
 */
public interface ICommonSysLangInfoService {
    /**
     * 根据表面，列名，记录id和当前语言更新多语言
     *
     * @param tableName
     * @param columnName
     * @param recordId
     * @param value
     * @return
     */
    int updateLangInfoByTableNameAndColumnNameAndRecordId(String tableName, String columnName, Long recordId, String value);

    /**
     * 根据记录ids和表名删除多语言
     *
     * @param tableName
     * @param recordIds
     * @return
     */
    int deleteLangInfoByRecordIdsAndTableName(String tableName, Long[] recordIds);
}
