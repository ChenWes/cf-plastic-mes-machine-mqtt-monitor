package com.cf.system.mapper;

import com.cf.common.core.domain.dto.PagingSysLangInfoQryDto;
import com.cf.common.core.domain.entity.SysLangInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 系统多语言数据接口
 * @Author ccw
 * @Date 2021/5/10
 */
public interface SysLangInfoMapper {

    /**
     * 根据条件分页查询角色数据
     *
     * @param langInfo
     * @return 数据集合信息
     */
    List<SysLangInfo> getLangInfoList(SysLangInfo langInfo);

    /**
     * 获取多语言信息
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
     * 单个删除多语言
     *
     * @param langInfoId
     * @return
     */
    int deleteLangInfoById(Long langInfoId);

    /**
     * 多个删除多语言
     *
     * @param langInfoIds
     * @return
     */
    int deleteLangInfoByIds(Long[] langInfoIds);

    /**
     * 根据表名,列名，记录id和语言更新多语言
     *
     * @param langInfo
     * @return
     */
    int updateLangInfoByTableNameAndColumnNameAndRecordId(SysLangInfo langInfo);

    /**
     * 根据记录ids和表名删除多语言
     *
     * @param tableName
     * @param recordIds
     * @return
     */
    int deleteLangInfoByRecordIdsAndTableName(@Param("tableName") String tableName, @Param("recordIds") Long[] recordIds);

    /**
     * 统计记录条数
     *
     * @param lang
     * @param tableName
     * @param columnName
     * @return
     */
    long getLangInfoCount(@Param("lang") String lang, @Param("tableName") String tableName, @Param("columnName") String columnName);

    List<SysLangInfo> getPagingLangInfoList(PagingSysLangInfoQryDto qryDto);
}
