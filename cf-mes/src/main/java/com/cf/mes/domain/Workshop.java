package com.cf.mes.domain;

import com.cf.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 车间信息对象 workshop
 * 
 * @author luorog
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Workshop extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 车间ID */
    private Long workshopId;

    /** 工厂ID */
    private Long factoryId;

    /**
     * 工厂
     */
    private Factory factoryEntity;

    /** 车间编码 */
    private String workshopCode;

    /** 车间名称 */
    private String workshopName;

    /** 状态 */
    private String status;
    
}
