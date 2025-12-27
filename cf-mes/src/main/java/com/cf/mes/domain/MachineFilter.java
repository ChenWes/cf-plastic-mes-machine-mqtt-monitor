package com.cf.mes.domain;

import com.cf.common.constant.MessageCode;
import com.cf.common.core.domain.BaseEntity;
import com.cf.common.core.domain.entity.SysDic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @Description 机器查询实体
 * @Author WesChen
 * @Date 2021-08-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MachineFilter extends Machine {
    private static final long serialVersionUID = 1L;

    /**
     * 工序id
     */
    private Long processId;
}
