package com.cf.mes.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 模温机-机器参数
 */
@Data
public class MouldTemperatureMachineParams implements Serializable {

    /**
     * 辅机器ID
     */
    private Long supportMachineId;

    /**
     * 辅机编号
     */
    private String supportMachineCode;

    /**
     * 模温机参数数据
     */
    private Map<String, Map<String, Object>> params;

}
