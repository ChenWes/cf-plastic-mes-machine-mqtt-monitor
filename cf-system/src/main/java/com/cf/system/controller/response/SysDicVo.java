package com.cf.system.controller.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 系统字典对象 sys_dic
 *
 * @author WesChen
 */
@Data
public class SysDicVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字典ID
     */
    private Long dicId;

    /**
     * 字典类型
     */
    private String dicType;

    /**
     * 字典编码
     */
    private String dicCode;

    /**
     * 字典名称
     */
    private String dicName;

    /**
     * 字典键值
     */
    private String dicValue;
}
