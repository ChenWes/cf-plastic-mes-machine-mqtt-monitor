package com.cf.common.core.domain.entity;

import com.cf.common.constant.MessageCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cf.common.annotation.Excel;
import com.cf.common.core.domain.BaseEntity;

/**
 * 系统字典对象 sys_dic
 * 
 * @author WesChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 字典ID */
    private Long dicId;

    /** 字典类型 */
    @Excel(name = MessageCode.DIC_DIC_TYPE_NAME)
    private String dicType;

    /** 字典编码 */
    @Excel(name = MessageCode.DIC_DIC_CODE_NAME)
    private String dicCode;

    /** 字典名称 */
    @Excel(name = MessageCode.DIC_DIC_NAME_NAME)
    private String dicName;

    /** 字典键值 */
    @Excel(name = MessageCode.DIC_DIC_VALUE_NAME)
    private String dicValue;

    /** 顺序号 */
    @Excel(name = MessageCode.DIC_SEQ_NO_NAME)
    private Long seqNo;

    /** 状态 */
    @Excel(name = MessageCode.DIC_STATUS_NAME)
    private String status;

    /** 父节点ID */
    @Excel(name = MessageCode.DIC_PARENT_ID_NAME)
    private Long parentId;

}
