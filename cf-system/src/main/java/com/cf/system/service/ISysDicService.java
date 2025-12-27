package com.cf.system.service;

import com.cf.common.core.domain.entity.SysDic;

import java.util.List;

/**
 * 系统字典Service接口
 *
 * @author WesChen
 */
public interface ISysDicService {
    /**
     * 查询系统字典
     *
     * @param dicId 系统字典ID
     * @return 系统字典
     */
    SysDic getSysDicById(Long dicId);

    /**
     * 查询系统字典列表
     *
     * @param sysDic 系统字典
     * @return 系统字典集合
     */
    List<SysDic> getSysDicList(SysDic sysDic);

    List<SysDic> getSysDicListByDicType(String dicType);

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
     * 批量删除系统字典
     *
     * @param dicIds 需要删除的系统字典ID
     * @return 结果
     */
    int deleteSysDicByIds(Long[] dicIds);

    /**
     * 删除系统字典信息
     *
     * @param dicId 系统字典ID
     * @return 结果
     */
    int deleteSysDicById(Long dicId);
}
