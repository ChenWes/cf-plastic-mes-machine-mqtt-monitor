package com.cf.mes.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author ccw
 * @Date 11/11/2021
 */
@Data
public class MachineDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 机器编码
     */
    private String machineCode;

    private String machineName;


    private Long workshopId;
}
