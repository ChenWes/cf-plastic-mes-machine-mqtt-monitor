package com.cf.mes.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 获取机器组织信息
 */
@Data
public class OrganInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long machineId;

    private Long factoryId;

    private Long workshopId;

}
