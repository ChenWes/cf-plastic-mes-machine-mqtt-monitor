package com.cf.mes.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cf.common.core.domain.BaseEntity;

/**
 * 工厂信息对象 factory
 * 
 * @author luorog
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Factory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工厂ID */
    private Long factoryId;

    /** 工厂编码 */
    private String factoryCode;

    /** 工厂名称 */
    private String factoryName;

    /** 状态 */
    private String status;

}
