package com.cf.system.service;

import com.cf.common.core.domain.dto.PagingSysLangInfoQryDto;
import com.cf.common.core.domain.entity.SysLangInfo;

import java.util.List;

/**
 * @Description 系统多语言服务接口
 * @Author ccw
 * @Date 2021/5/10
 */
public interface ISysLangInfoService {

    /**
     * 根据条件分页查询据
     *
     * @param langInfo
     * @return
     */
    List<SysLangInfo> getLangInfoList(SysLangInfo langInfo);

    /**
     * 根据语言获取多语言列表
     *
     * @param lang
     * @return
     */
    List<SysLangInfo> getLangInfoByLang(String lang);


    List<SysLangInfo> getLangInfoByLangAndTableName(String lang, String tableName);

    List<SysLangInfo> getPagingLangInfoList(PagingSysLangInfoQryDto qryDto);

    /**
     * 根据id获取多语言
     *
     * @param langInfoId
     * @return
     */
    SysLangInfo getLangInfoById(Long langInfoId);

    /**
     * 插入多语言
     *
     * @param langInfo
     * @return
     */
    int insertLangInfo(SysLangInfo langInfo);

    /**
     * 更新多语言
     *
     * @param langInfo
     * @return
     */
    int updateLangInfo(SysLangInfo langInfo);

    /**
     * 批量插入多语言
     *
     * @param langInfoList
     * @return
     */
    int batchSaveLangInfo(List<SysLangInfo> langInfoList);

    /**
     * 根据lang, table , column 查询记录条数
     *
     * @param lang
     * @param tableName
     * @param columnName
     * @return
     */
    long getLangInfoCount(String lang, String tableName, String columnName);

    /**
     * 根据id删除多语言
     * 2021-11-03 WesChen 目前是没有删除多语言的操作，所以删除该方法
     * @param langInfoId
     * @return
     */
//    int deleteLangInfoById(Long langInfoId);

    /**
     * 根据ids批量删除多语言
     * 2021-11-03 WesChen 目前是没有删除多语言的操作，所以删除该方法
     * @param langInfoId
     * @return
     */
//    int deleteLangInfoByIds(Long[] langInfoId);
}
