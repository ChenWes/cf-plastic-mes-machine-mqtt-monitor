package com.cf.mes.domain;

import com.cf.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 机器状态实体
 * @Author ccw
 * @Date 2021/5/13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MachineStatus extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 机器状态id
     */
    private Long machineStatusId;

    /**
     * 机器类型id
     */
    private Long machineTypeId;

    /**
     * 机器类型
     */
    private MachineType machineTypeEntity;

    /**
     * 机器状态code
     */
    private String machineStatusCode;

    /**
     * 机器状态name
     */
    private String machineStatusName;

    /**
     * 机器状态颜色
     */
    private String statusColorCode;

    /**
     * 使用状态
     */
    private String status;
}
