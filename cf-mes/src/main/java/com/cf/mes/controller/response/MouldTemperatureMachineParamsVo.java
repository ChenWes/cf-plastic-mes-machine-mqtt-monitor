package com.cf.mes.controller.response;

import com.cf.mes.domain.dto.MouldTemperatureMachineParams;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

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
     * 温度下限（摄氏度）
     */
    private BigDecimal minTemperature;

    /**
     * 温度上限（摄氏度）
     */
    private BigDecimal maxTemperature;

    /**
     * 模温机参数数据
     */
    private MouldTemperatureMachineParams machineParams;

    /*
     * 判断是否在设定配置的的范围中
     */
    private boolean inSettingsTempRange;

    /**
     * 判断是否在机器设定的的范围中
     */
    private boolean inMachineTempRange;

}
