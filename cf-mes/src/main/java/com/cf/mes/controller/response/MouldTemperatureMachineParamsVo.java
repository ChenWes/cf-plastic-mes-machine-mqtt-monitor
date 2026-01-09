package com.cf.mes.controller.response;

import com.cf.mes.domain.dto.MouldTemperatureMachineParams;
import lombok.Data;

import java.io.Serializable;

/**
 * 模温机-机器参数
 */
@Data
public class MouldTemperatureMachineParamsVo implements Serializable {

    /**
     * 辅机器ID
     */
    private Long machineId;

    /**
     * 辅机编号
     */
    private Long settingId;

    /**
     * 模温机参数数据
     */
    private MouldTemperatureMachineParams params;

}
