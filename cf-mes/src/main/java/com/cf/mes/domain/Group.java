package com.cf.mes.domain;

import com.cf.common.annotation.Excel;
import com.cf.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 组别实体
 * @Author ccw
 * @Date 2021/5/11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Group extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 组别id
     */
    private Long groupId;

    /**
     * 所属部门id
     */
    private Long deptId;

    /**
     * 所属部门
     */
    private Dept deptEntity;

    /**
     * 组别编码
     */
    private String groupCode;

    /**
     * 组别名称
     */
    private String groupName;

    /**
     * 组别状态
     */
    private String status;
}
