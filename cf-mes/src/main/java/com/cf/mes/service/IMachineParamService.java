package com.cf.mes.service;

import com.cf.mes.domain.dto.MouldTemperatureMachineParams;

/**
 * 机器参数服务接口
 */
public interface IMachineParamService {

    /**
     * 获取模温机器参数
     *
     * @param machineId
     * @param machineCode
     * @return
     */
    MouldTemperatureMachineParams getMouldTemperatureMachineParams(Long machineId, String machineCode);

}
