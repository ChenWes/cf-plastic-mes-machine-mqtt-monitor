package com.cf.mes.domain;

import com.cf.common.constant.MessageCode;
import com.cf.common.core.domain.BaseEntity;
import com.cf.common.core.domain.entity.SysDic;
import com.cf.mes.domain.clientStatusMonitor.ClientStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @Description 机器实体
 * @Author ccw
 * @Date 2021/5/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Machine extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 机器id
     */
    private Long machineId;

    /**
     * 车间id
     */
    @NotNull(message = MessageCode.WORKSHOP_WORKSHOP_ID_EMPTY)
    private Long workshopId;

    /**
     * 所属车间
     */
    private Workshop workshopEntity;

    /**
     * 机器编码
     */
    private String machineCode;

    /**
     * 机器名称
     */
    private String machineName;

    /**
     * 机器类型id
     */
    @NotNull(message = MessageCode.MACHINE_TYPE_MACHINE_TYPE_ID_EMPTY)
    private Long machineTypeId;

    /**
     * 机器类型实体
     */
    private MachineType machineTypeEntity;

    /**
     * 机器品牌
     */
    private String machineBrand;

    /**
     * 机器品牌字典
     */
    private SysDic machineBrandDicEntity;

    /**
     * 机器Ip
     */
    private String machineIp;

    /**
     * 机器网卡MAC
     */
    private String macAddress;

    /**
     * 机器网卡MAC
     */
    private String capacity;

    /**
     * 机器状态
     */
    private String status;


    /**
     * 机器注册码（默认是新增机器时，插入的随机GUID码）
     */
    private String registrationCode;


    /**
     * 机器参数字符串
     */
    private String parameterString;


    private boolean onLine;

    private ClientStatus clientStatus;
}
