package com.cf.mes.domain;

import com.cf.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 机器类型
 * @Author ccw
 * @Date 2021/5/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MachineType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 机器类型id
     */
    private Long  machineTypeId;

    /**
     * 机器类型code
     */
    private String machineTypeCode;

    /**
     * 机器类型name
     */
    private String machineTypeName;


    /**
     * 状态
     */
    private String status;
}
