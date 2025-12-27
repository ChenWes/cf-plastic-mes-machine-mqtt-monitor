package com.cf.system.service.impl;

import com.cf.common.annotation.DeleteLangInfoCache;
import com.cf.common.annotation.PutLangInfoCache;
import com.cf.common.constant.MessageCode;
import com.cf.common.core.domain.dto.PagingSysLangInfoQryDto;
import com.cf.common.core.domain.entity.SysLangInfo;
import com.cf.common.core.lang.ICommonSysLangInfoService;
import com.cf.common.core.token.ITokenService;
import com.cf.common.exception.CustomException;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.MessageUtils;
import com.cf.system.mapper.SysLangInfoMapper;
import com.cf.system.service.ISysLangInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description 系统多语言业务处理
 * @Author ccw
 * @Date 2021/5/10
 */
@Service
public class SysLangInfoServiceImpl implements ISysLangInfoService, ICommonSysLangInfoService {

    @Autowired
    private SysLangInfoMapper langInfoMapper;
    @Autowired
    private ITokenService tokenService;


    @Override
    public List<SysLangInfo> getLangInfoList(SysLangInfo langInfo) {
        return this.langInfoMapper.getLangInfoList(langInfo);
    }

    @Override
    public List<SysLangInfo> getPagingLangInfoList(PagingSysLangInfoQryDto qryDto) {
        return this.langInfoMapper.getPagingLangInfoList(qryDto);
    }

    @Override
    public SysLangInfo getLangInfoById(Long langInfoId) {
        return this.langInfoMapper.getLangInfoById(langInfoId);
    }

    @Override
    public List<SysLangInfo> getLangInfoByLang(String lang) {
        SysLangInfo langInfo = new SysLangInfo();
        //语言类型
        langInfo.setLang(lang);

        return this.langInfoMapper.getLangInfoList(langInfo);
    }

    @Override
    public List<SysLangInfo> getLangInfoByLangAndTableName(String lang, String tableName) {

        SysLangInfo langInfo = new SysLangInfo();
        //语言类型
        langInfo.setLang(lang);
        //表名
        langInfo.setTableName(tableName);

        List<SysLangInfo> sysLangInfoList = this.langInfoMapper.getLangInfoList(langInfo);

        return sysLangInfoList;
    }

    public SysLangInfo getLangInfoByKey(Long recordId, String tableName, String columnName, String lang) {

        SysLangInfo langInfo = new SysLangInfo();
        langInfo.setRecordId(recordId);
        langInfo.setTableName(tableName);
        langInfo.setColumnName(columnName);
        langInfo.setLang(lang);

        //通过记录ID、表名、列名找出记录
        List<SysLangInfo> list = this.langInfoMapper.getLangInfoList(langInfo);

        if (list != null && list.size() > 0) {
            //如果找到记录，则返回第一条
            return list.get(0);
        }
        return null;
    }


    @Override
    //@SetLangInfoCache
    @PutLangInfoCache(tableName = "#langInfo.tableName", columnName = "#langInfo.columnName", recordId = "#langInfo.recordId", value = "#langInfo.value")
    @Transactional(rollbackFor = Exception.class)
    public int insertLangInfo(SysLangInfo langInfo) {

        langInfo.setTableName(langInfo.getTableName().toLowerCase());
        langInfo.setColumnName(langInfo.getColumnName().toLowerCase());

        //check data
        SysLangInfo sysLangInfo = this.getLangInfoByKey(langInfo.getRecordId(), langInfo.getTableName(), langInfo.getColumnName(), langInfo.getLang());

        if (sysLangInfo != null) {
            String msg = MessageUtils.message(MessageCode.LANG_INFO_KEY_EXISTS);
            throw new CustomException(msg);
        }
        langInfo.setCreateTime(DateUtils.getNowDate());
        return this.langInfoMapper.insertLangInfo(langInfo);
    }

    @Override
    // @SetLangInfoCache
    @PutLangInfoCache(tableName = "#langInfo.tableName", columnName = "#langInfo.columnName", recordId = "#langInfo.recordId", value = "#langInfo.value")
    public int updateLangInfo(SysLangInfo langInfo) {

        //修改时间
        langInfo.setUpdateTime(DateUtils.getNowDate());
        //修改人
        langInfo.setUpdateBy(tokenService.getUserCode());

        return this.langInfoMapper.updateLangInfo(langInfo);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @PutLangInfoCache(value = "#langInfoList", multi = true)
    public int batchSaveLangInfo(List<SysLangInfo> langInfoList) {

        //批量进行保存，按新增或修改进行处理
        for (SysLangInfo item : langInfoList) {

            if (item.getLangInfoId() == 0) {
                //新增
                this.insertLangInfo(item);
            } else {
                //修改
                this.updateLangInfo(item);
            }

        }
        //清除缓存
//        LangInfoUtils.clearLangInfoCache();

        //清除某个表的数据缓存
        /*if (langInfoList != null && langInfoList.size() > 0) {
            String tableName = langInfoList.get(0).getTableName();
            LangInfoUtils.clearAllLangInfoCacheByTableName(tableName);
        }*/

        return langInfoList.size();
    }

    @Override
    public long getLangInfoCount(String lang, String tableName, String columnName) {
        return langInfoMapper.getLangInfoCount(lang, tableName, columnName);
    }

//    @Override
//    @SetLangInfoCache
//    public int deleteLangInfoById(Long langInfoId) {
//
//        //删除某个多语言的值
//        return this.deleteLangInfoById(langInfoId);
//    }

//    @Override
//    @SetLangInfoCache
//    public int deleteLangInfoByIds(Long[] langInfoIds) {
//
//        //删除多个多语言的值
//        return this.deleteLangInfoByIds(langInfoIds);
//    }

    @Override
    //@SetLangInfoCache
    @PutLangInfoCache(tableName = "#tableName", columnName = "#columnName", recordId = "#recordId", value = "#value")
    public int updateLangInfoByTableNameAndColumnNameAndRecordId(String tableName, String columnName, Long recordId, String value) {

        //先设置记录实体
        SysLangInfo sysLangInfo = new SysLangInfo();

        sysLangInfo.setTableName(tableName);
        sysLangInfo.setColumnName(columnName);
        sysLangInfo.setRecordId(recordId);
        sysLangInfo.setLang(tokenService.getCurrentLangType().toString());
        sysLangInfo.setValue(value);
        sysLangInfo.setUpdateBy(tokenService.getUserCode());
        sysLangInfo.setUpdateTime(DateUtils.getNowDate());
        sysLangInfo.setTableName(sysLangInfo.getTableName().toLowerCase());
        sysLangInfo.setColumnName(sysLangInfo.getColumnName().toLowerCase());


        //尝试修改记录
        int row = langInfoMapper.updateLangInfoByTableNameAndColumnNameAndRecordId(sysLangInfo);

        if (row > 0) {

            //修改成功则进行处理
            return row;

        } else {

            //如果记录不存在则进行新增
            sysLangInfo.setCreateBy(tokenService.getUserCode());
            sysLangInfo.setCreateTime(DateUtils.getNowDate());

            //新增记录
            row = langInfoMapper.insertLangInfo(sysLangInfo);

            return row;
        }
    }

    /**
     * 根据记录ids和表名删除多语言
     *
     * @param tableName
     * @param recordIds
     * @return
     */
    @Override
    //@SetLangInfoCache
    @DeleteLangInfoCache(tableName = "#tableName", value = "#recordIds", field = "record_id", multi = true)
    public int deleteLangInfoByRecordIdsAndTableName(String tableName, Long[] recordIds) {

        //删除某个表中的，某此ID的记录
        return langInfoMapper.deleteLangInfoByRecordIdsAndTableName(tableName, recordIds);
    }


}
