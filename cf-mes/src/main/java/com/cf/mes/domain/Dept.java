package com.cf.mes.domain;

import com.cf.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门对象 dept
 * 
 * @author luorog
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Dept extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 部门ID */
    private Long deptId;

    /** 部门编码 */
    private String deptCode;

    /** 部门名称 */
    private String deptName;

    /** 状态 */
    private String status;

}
