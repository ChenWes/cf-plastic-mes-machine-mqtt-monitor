package com.cf.common.config.biz.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 机器使用率归类
 *
 * @author mao
 */
@Data
public class MachineUsageRateField implements Serializable {
    private String field;
    private String name;
    private List<String> status;
    private String desc;
    private Map<String, String> i18n;
}
