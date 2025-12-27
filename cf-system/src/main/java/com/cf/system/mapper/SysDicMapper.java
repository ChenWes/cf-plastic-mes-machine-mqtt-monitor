package com.cf.system.mapper;

import com.cf.common.core.domain.entity.SysDic;

import java.util.List;

/**
 * 系统字典Mapper接口
 *
 * @author WesChen
 */
public interface SysDicMapper {
    /**
     * 查询系统字典
     *
     * @param dicId 系统字典ID
     * @return 系统字典
     */
    SysDic getSysDicById(Long dicId);

    /**
     * 查询系统词典
     *
     * @param dicCode 系统字典code
     * @return 系统字典
     */
    SysDic getSysDicByCode(String dicCode);

    /**
     * 查询系统词典
     *
     * @param dicCodes 系统字典code列表
     * @return 系统字典列表
     */
    List<SysDic> getSysDicListByDicCodes(List<String> dicCodes);


    /**
     * 查询系统字典列表
     *
     * @param sysDic 系统字典
     * @return 系统字典集合
     */
    List<SysDic> getSysDicList(SysDic sysDic);

    /**
     * 新增系统字典
     *
     * @param sysDic 系统字典
     * @return 结果
     */
    int insertSysDic(SysDic sysDic);

    /**
     * 修改系统字典
     *
     * @param sysDic 系统字典
     * @return 结果
     */
    int updateSysDic(SysDic sysDic);

    /**
     * 删除系统字典
     *
     * @param dicId 系统字典ID
     * @return 结果
     */
    int deleteSysDicById(Long dicId);

    /**
     * 批量删除系统字典
     *
     * @param dicIds 需要删除的数据ID
     * @return 结果
     */
    int deleteSysDicByIds(Long[] dicIds);
}
