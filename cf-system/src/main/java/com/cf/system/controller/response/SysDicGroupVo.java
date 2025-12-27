package com.cf.system.controller.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 系统字典对象 sys_dic
 *
 * @author WesChen
 */
@Data
public class SysDicGroupVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 字典类型
     */
    private String dicType;

    private List<SysDicVo> list;

}
