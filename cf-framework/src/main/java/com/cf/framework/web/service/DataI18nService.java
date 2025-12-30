package com.cf.framework.web.service;

import com.cf.common.core.domain.dto.PagingSysLangInfoQryDto;
import com.cf.common.core.domain.entity.SysLangInfo;
import com.cf.common.core.i18n.IDataI18nService;
import com.cf.common.core.redis.RedisCache;
import com.cf.common.core.redis.type.Hash;
import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoTableName;
import com.cf.common.enums.LangInfoType;
import com.cf.common.utils.StringUtils;
import com.cf.common.utils.reflect.ReflectUtils;
import com.cf.system.service.I18nService;
import com.cf.system.service.ISysLangInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 数据国际化。国际化数据使用频繁-常驻缓存中
 * @Author wilfmao
 * @Date 2023/07/04
 */
@Component
@Slf4j
public class DataI18nService implements IDataI18nService {

    private static final int SYNC_BATCH_SIZE = 200;

    private static final int DEL_BATCH_SIZE = 500;

    private static final String PREFIX = "I18N";

    @Value("${i18n.data.init.enable}")
    private Boolean i18nDataInitEnable;

    @Autowired
    private ISysLangInfoService sysLangInfoService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private I18nService i18nService;

    /**
     * 首次加载全量更新缓存
     * TODO: 后期重构需要对初始化缓存逻辑进行优化
     * 方案1: 离线全量同步 + CDC
     * 方案2: 离线全量刷新 + @SetLangCache注解
     */
    @PostConstruct
    @Override
    public void initCache() {
        if (!i18nDataInitEnable) {
            return;
        }
        List<String> langs = Arrays.stream(LangInfoType.values()).map(String::valueOf).collect(Collectors.toList());
        List<String> tables = Arrays.stream(LangInfoTableName.values()).map(LangInfoTableName::getValue)
                .collect(Collectors.toList());
        for (String lang : langs) {
            // 异步执行线程处理,防止阻塞主线程正常启动
            threadPoolTaskExecutor.submit(() -> {
                for (String tableName : tables) {
                    loadCache(lang, tableName, null);
                }
            });
        }
    }

    @Override
    public void loadCache(String lang, String tableName, String columnName) {
        // 获取记录总数
        long total = sysLangInfoService.getLangInfoCount(lang, tableName, columnName);
        if (total == 0) {
            return;
        }
        // 分批同步redis
        int pages = (int) (total % SYNC_BATCH_SIZE == 0 ? total / SYNC_BATCH_SIZE : total / SYNC_BATCH_SIZE + 1);
        List<SysLangInfo> list;
        Long lastId = null;
        PagingSysLangInfoQryDto qryDto;
        for (int i = 0; i < pages; i++) {
            // 获取数据
            qryDto = new PagingSysLangInfoQryDto();
            qryDto.setTableName(tableName);
            qryDto.setColumnName(columnName);
            qryDto.setLang(lang);
            qryDto.setPageSize(SYNC_BATCH_SIZE);
            // 设置最后一条记录ID,用于分页处理
            qryDto.setLastId(lastId);

            list = sysLangInfoService.getPagingLangInfoList(qryDto);
            // 执行同步
            batchCache(list);

            if (CollectionUtils.isNotEmpty(list)) {
                // 设置最后一条记录ID,用于分页处理
                lastId = list.get(list.size() - 1).getLangInfoId();
            }
        }
    }

    @Override
    public void batchCache(List<SysLangInfo> langInfos) {
        // 封装redis hash数据： key , field , value
        List<Hash<String, String, SysLangInfo>> hashDataList = new ArrayList<>();
        String key;
        Hash<String, String, SysLangInfo> hashData;
        for (SysLangInfo info : langInfos) {
            if (info == null) {
                continue;
            }
            info.setValue(StringUtils.defaultString(info.getValue()));
            hashData = new Hash<>();
            key = concatKey(info.getLang(), info.getTableName(), info.getColumnName());
            hashData.setKey(key);
            hashData.setField(String.valueOf(info.getRecordId()));
            hashData.setValue(info);
            hashDataList.add(hashData);
        }
        // 管道批量操作
        redisCache.executePipelined(hashDataList);
    }

    @Override
    public void cache(SysLangInfo langInfo) {
        String key = concatKey(langInfo.getLang(), langInfo.getTableName(), langInfo.getColumnName());
        String field = String.valueOf(langInfo.getRecordId());
        langInfo.setValue(StringUtils.defaultString(langInfo.getValue()));
        redisCache.setCacheMapValue(key, field, langInfo);
    }

    @Override
    public void deleteCache(String lang, String tableName, String columnName) {
        List<String> genKeys = generateKeys(lang, tableName, columnName);
        List<List<String>> partition = ListUtils.partition(genKeys, DEL_BATCH_SIZE);
        for (List<String> keys : partition) {
            redisCache.executeDelPipelined(keys);
        }
    }

    @Override
    public void deleteCache(String lang, String tableName, String columnName, String recordId) {
        String key = concatKey(lang, tableName, columnName);
        if (StringUtils.isNotEmpty(recordId)) {
            deleteCache(key, recordId);
        } else {
            if (redisCache.hasKey(key)) {
                redisCache.deleteObject(key);
            }
        }
    }

    @Override
    public void deleteCache(String key, String... hKeys) {
        redisCache.delCacheMapValue(key, hKeys);
    }

    @Override
    public SysLangInfo getI18nInfo(String lang, String tableName, String columnName, String recordId) {
        String key = concatKey(lang, tableName, columnName);
        return redisCache.getCacheMapValue(key, recordId);
    }

    @Override
    public boolean existCacheKey(String lang, String tableName, String columnName) {
        String key = concatKey(lang, tableName, columnName);
        return redisCache.hasKey(key);
    }

    @Override
    public void translate(String tableName, String columnName, String keyFieldName, String lang, Object obj) {
        // fast-fail
        if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(columnName)) {
            return;
        }
        // 判断key是否存在
        boolean hasKey = existCacheKey(lang, tableName, columnName);
        if (!hasKey) {
            return;
        }
        //通过反射获取从数据库查询出来的对象获取key值
        Long id = ReflectUtils.getFieldValue(obj, keyFieldName);
        //如果key值为空则不处理
        if (id == null || id <= 0) {
            return;
        }
        // 表名及列名都不为空是进行国际化
        // 获取国际化信息
        SysLangInfo langInfo = getI18nInfo(lang, tableName, columnName, String.valueOf(id));
        // 若多语言数据不为空，则对数据进行国际化
        if (langInfo != null && StringUtils.isNotEmpty(langInfo.getValue())) {
            // 下划线转驼峰
            String filedName = StringUtils.toCamelCase(columnName);
            ReflectUtils.setFieldValue(obj, filedName, langInfo.getValue());
        }
    }

    /**
     * 生成 redis hash key值
     *
     * @param filterLang
     * @param filterTable
     * @param filterColumn
     * @return
     */
    @Override
    public List<String> generateKeys(String filterLang, String filterTable, String filterColumn) {
        List<String> langs = Arrays.stream(LangInfoType.values()).map(String::valueOf).collect(Collectors.toList());
        List<LangInfoColumnName> columns = Arrays.stream(LangInfoColumnName.values()).collect(Collectors.toList());

        // filter
        if (StringUtils.isNotEmpty(filterLang)) {
            langs = langs.stream().filter(r -> r.equals(filterLang)).collect(Collectors.toList());
        }
        if (StringUtils.isNotEmpty(filterTable)) {
            columns = columns.stream().filter(r -> r.getTableName().getValue().equals(filterTable)).collect(Collectors.toList());
        }
        if (StringUtils.isNotEmpty(filterColumn)) {
            columns = columns.stream().filter(r -> r.getValue().equals(filterColumn)).collect(Collectors.toList());
        }

        // gen key
        List<String> list = new ArrayList<>();
        String key;
        for (String lang : langs) {
            for (LangInfoColumnName column : columns) {
                key = concatKey(lang, column.getTableName().getValue(), column.getValue());
                list.add(key);
            }
        }
        return list;
    }

    @Override
    public void repair(String lang, String tableSchema) {
        i18nService.repair(lang, tableSchema);
    }

    /**
     * 拼接 redis hash key值
     *
     * @param lang
     * @param tableName
     * @param columnName
     * @return
     */
    private String concatKey(String lang, String tableName, String columnName) {
        return PREFIX + ":" + lang + ":" + tableName + ":" + columnName;
    }

}
