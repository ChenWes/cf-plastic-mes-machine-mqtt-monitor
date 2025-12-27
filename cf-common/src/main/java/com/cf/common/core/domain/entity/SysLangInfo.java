package com.cf.common.core.domain.entity;

import com.cf.common.constant.MessageCode;
import com.cf.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * @Description 系统多语言设置表
 * @Author ccw
 * @Date 2021/5/10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLangInfo extends BaseEntity {

    private Long langInfoId;
    private String tableName;
    private String columnName;
    private Long recordId;
    private String lang;
    private String value;

}
