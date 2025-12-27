package com.cf.mes.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author ccw
 * @Date 11/11/2021
 */
@Data
public class DefectDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long defectId;
    /**
     * 疵点名称
     */
    private String defectName;
}
