package com.cf.system.controller.convert;

/**
 * @author coder-ren
 * @date 2024/5/17 14:51
 */

import com.cf.common.core.domain.entity.SysDic;
import com.cf.system.controller.response.SysDicVo;

/**
 * 启用映射转换
 */
public class SysDicConvert {

    public static SysDicVo toSysDicVo(SysDic sysDic) {
        if (sysDic == null) {
            return null;
        }

        SysDicVo sysDicVo = new SysDicVo();

        sysDicVo.setDicId(sysDic.getDicId());
        sysDicVo.setDicType(sysDic.getDicType());
        sysDicVo.setDicCode(sysDic.getDicCode());
        sysDicVo.setDicName(sysDic.getDicName());
        sysDicVo.setDicValue(sysDic.getDicValue());

        return sysDicVo;
    }

}
