package com.cf.system.service.impl;

import com.cf.common.core.domain.entity.SysLangInfo;
import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoTableName;
import com.cf.system.mapper.I18nMapper;
import com.cf.system.service.I18nService;
import com.cf.system.service.ISysLangInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class I18nServiceImpl implements I18nService {

    @Autowired
    private I18nMapper i18nMapper;

    @Autowired
    private ISysLangInfoService sysLangInfoService;

    @Override
    public List<Map<String, String>> getPrimaryKeyNames(String tableSchema, List<String> tableNames) {
        return i18nMapper.getPrimaryKeyNames(tableSchema, tableNames);
    }

    private List<Map<String, Object>> getDataList(String sql) {
        return i18nMapper.getDataList(sql);
    }

    @Override
    public void repair(String lang, String tableSchema) {
        if (StringUtils.isEmpty(lang) || StringUtils.isEmpty(tableSchema)) {
            log.info("lang or tableSchema is empty!!!");
            return;
        }
        // 获取table名称
        List<String> tables = new ArrayList<>();
        for (LangInfoColumnName value : LangInfoColumnName.values()) {
            LangInfoTableName tableName = value.getTableName();
            if (tableName == null || StringUtils.isEmpty(tableName.getValue())) {
                continue;
            }
            tables.add(tableName.getValue());
        }
        if (tables.isEmpty()) {
            log.info("tables is empty!!!");
            return;
        }

        // 获取表主键列名称
        List<Map<String, String>> primaryKeyNames = this.getPrimaryKeyNames(tableSchema, tables);
        if (primaryKeyNames.isEmpty()) {
            log.info("primaryKeyNames is empty!!!");
            return;
        }

        String sqlTpl = "SELECT `%s`,`%s` FROM `%s`";
        for (LangInfoColumnName value : LangInfoColumnName.values()) {
            LangInfoTableName tableName = value.getTableName();
            if (tableName == null) {
                continue;
            }
            String table = tableName.getValue();
            // 获取主键信息
            Map<String, String> primaryKeyMap = primaryKeyNames.stream().filter(r -> r.get("TABLE_NAME").equals(table))
                    .findFirst().orElse(null);
            if (primaryKeyMap == null) {
                return;
            }
            String primaryKey = primaryKeyMap.get("COLUMN_NAME");
            // 国际化字段
            String i18nColumn = value.getValue();
            // 拼接sql
            String sql = String.format(sqlTpl, primaryKey, i18nColumn, table);
            // 获取需要国际化的表数据
            List<Map<String, Object>> dataList = this.getDataList(sql);
            // 封装国际化数据信息
            List<SysLangInfo> sysLangInfos = toSysLangInfoList(dataList, lang, table, primaryKey, i18nColumn);
            if (CollectionUtils.isEmpty(sysLangInfos)) {
                continue;
            }
            // 修复数据
            log.info("i18n repair start ==> lang:{}, table:{}, primaryKey:{}, column:{}", lang, table, primaryKey, i18nColumn);
            int insertCount = 0;
            int updateCount = 0;
            for (SysLangInfo sysLangInfo : sysLangInfos) {
                // 判断数据可以中是否已经存在记录
                List<SysLangInfo> exists = sysLangInfoService.getLangInfoList(sysLangInfo);
                if (CollectionUtils.isEmpty(exists)) {
                    // insert
                    sysLangInfoService.insertLangInfo(sysLangInfo);
                    insertCount++;
                } else {
                    // update
                    SysLangInfo exist = exists.get(0);
                    if (!StringUtils.isEmpty(exist.getValue())) {
                        // 若国际化数据已经存在不修复
                        continue;
                    }
                    if (StringUtils.isEmpty(exist.getValue()) && StringUtils.isEmpty(sysLangInfo.getValue())) {
                        // 若源记录的数据值为空 且 国际化记录数据值也为空，则不做处理
                        continue;
                    }

                    exist.setValue(StringUtils.defaultString(sysLangInfo.getValue()));
                    sysLangInfoService.updateLangInfo(exist);
                    updateCount++;
                }

            }
            log.info("i18n repair end ==> lang:{}, table:{}, primaryKey:{}, column:{}, dataSize:{}, repairInsertCount:{}, repairUpdateCount:{}",
                    lang, table, primaryKey, i18nColumn, sysLangInfos.size(), insertCount, updateCount);
        }
    }

    /**
     * 对象转换
     *
     * @param list
     * @param lang
     * @param table
     * @param primaryKey
     * @param i18nColumn
     * @return
     */
    private List<SysLangInfo> toSysLangInfoList(List<Map<String, Object>> list, String lang, String table,
                                                String primaryKey, String i18nColumn) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        SysLangInfo sysLangInfo;
        List<SysLangInfo> sysLangInfos = new ArrayList<>();
        for (Map<String, Object> map : list) {
            if (map == null || map.isEmpty()) {
                continue;
            }
            sysLangInfo = new SysLangInfo();
            sysLangInfo.setTableName(table);
            sysLangInfo.setColumnName(i18nColumn);
            Long recordId = (Long) map.get(primaryKey);
            sysLangInfo.setRecordId(recordId);
            sysLangInfo.setLang(lang);
            String value = (String) map.get(i18nColumn);
            sysLangInfo.setValue(value);
            sysLangInfo.setCreateBy("repair");
            sysLangInfo.setCreateTime(new Date());
            sysLangInfos.add(sysLangInfo);
        }
        return sysLangInfos;
    }
}

