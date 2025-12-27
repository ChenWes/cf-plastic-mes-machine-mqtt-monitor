package com.cf.common.core.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 系统多语言设置表分页查询DTO
 * @Author wilfmao
 * @Date 2024/7/18
 */
@Data
public class PagingSysLangInfoQryDto implements Serializable {
    private String tableName;
    private String columnName;
    private String lang;
    /**
     * 当前也最后一个ID
     */
    private Long lastId;
    /**
     * limit 记录条数
     */
    private Integer pageSize;
}
