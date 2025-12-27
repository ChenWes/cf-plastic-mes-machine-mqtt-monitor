package com.cf.mes.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author ccw
 * @Date 11/11/2021
 */
@Data
public class EmployeeDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 员工名称
     */
    private String employeeName;
}
