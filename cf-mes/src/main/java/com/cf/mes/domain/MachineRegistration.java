package com.cf.mes.domain;

import com.cf.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MachineRegistration extends BaseEntity {

    /**
     * 机器ID
     */
    private Long machineId;

    /**
     * 机器Ip
     */
    private String machineIp;

    /**
     * 机器网卡MAC
     */
    private String macAddress;


    /**
     * 机器注册码（默认是新增机器时，插入的随机GUID码）
     */
    private String registrationCode;
}
