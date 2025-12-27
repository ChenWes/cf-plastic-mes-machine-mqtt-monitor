package com.cf.common.core.dic;

import com.cf.common.core.domain.entity.SysDic;

import java.util.List;

/**
 * @Description
 * @Author ccw
 * @Date 2021/5/25
 */
public interface ICommonDicService {

    /**
     * 根据字典code查询系统字典
     *
     * @param dicCode
     * @return
     */
    SysDic getSysDicByCode(String dicCode);

    /**
     * 根据字典code列表查询系统字典
     *
     * @param dicCodes
     * @return
     */
    List<SysDic> getSysDicListByDicCodes(List<String> dicCodes);


}
