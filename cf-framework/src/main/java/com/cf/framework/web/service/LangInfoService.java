package com.cf.framework.web.service;


import com.cf.common.core.domain.entity.SysLangInfo;
import com.cf.common.enums.LangInfoType;
import com.cf.common.utils.LangInfoUtils;
import com.cf.system.service.ISysLangInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Description 多语言服务
 * @Author ccw
 * @Date 2021/5/10
 */
@Component
public class LangInfoService {

    @Autowired
    private ISysLangInfoService sysLangInfoService;

    public List<SysLangInfo> getLangInfoByLangAndTableName(String tableName, LangInfoType langInfoType) {
        //尝试从缓存中获取多语言数据
        List<SysLangInfo> list = LangInfoUtils.getLangInfoCache(tableName, langInfoType.toString());

        if (list == null || list.size() == 0) {
            //如果缓存中不存在则从数据库中获取记录
            ///list = langInfoService.getLangInfoByLang(langInfoType.toString());
            list = sysLangInfoService.getLangInfoByLangAndTableName(langInfoType.toString(), tableName);

            //将记录加入至缓存
            LangInfoUtils.setLangInfoCache(tableName, langInfoType.toString(), list);

            //再次从缓存中获取多语言
            list = LangInfoUtils.getLangInfoCache(tableName, langInfoType.toString());
        }

        return list;
    }

    /**
     * 在传入的所有语言数据中通过传入的原记录ID、表名、列名找出对应的多语言值
     *
     * @param recordId
     * @param tableName
     * @param columnName
     * @param list
     * @return
     */
    public SysLangInfo getLangInfoByLangRecord(long recordId, String tableName, String columnName, List<SysLangInfo> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        for (SysLangInfo item : list) {
            if (recordId == item.getRecordId() && tableName.toLowerCase().equals(item.getTableName()) && columnName.toLowerCase().equals(item.getColumnName())) {
                return item;
            }
        }
        return null;
    }
}
