package com.cf.mes.domain;

///import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import com.cf.common.annotation.Excel;
import com.cf.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
///import io.swagger.annotations.ApiModel;
///import io.swagger.annotations.ApiModelProperty;

/**
 * 生产任务关联模温机对象 production_task_mtc
 *
 * @author wilfmao
 * @date 2026-01-08
 */
///@ApiModel(value = "生产任务关联模温机")
public class ProductionTaskMtc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "${comment}")
    private Long id;

    /** 生产任务ID */
    @Excel(name = "生产任务ID")
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "生产任务ID")
    private Long taskId;

    /** 模温设定ID */
    @Excel(name = "模温设定ID")
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "模温设定ID")
    private Long settingsId;

    /** 辅机器ID */
    @Excel(name = "辅机器ID")
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "辅机器ID")
    private Long supportMachineId;

    /** 辅机编号 */
    @Excel(name = "辅机编号")
    //@ApiModelProperty(value = "辅机编号")
    private String supportMachineCode;

    /** 操作员工ID */
    @Excel(name = "操作员工ID")
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "操作员工ID")
    private Long employeeId;

    /** 操作员工编号 */
    @Excel(name = "操作员工编号")
    //@ApiModelProperty(value = "操作员工编号")
    private String employeeCode;

    /** 绑定标记：1 绑定, 0 解除绑定 */
    @Excel(name = "绑定标记：1 绑定, 0 解除绑定")
    //@ApiModelProperty(value = "绑定标记：1 绑定, 0 解除绑定")
    private Integer bindFlag;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setTaskId(Long taskId)
    {
        this.taskId = taskId;
    }

    public Long getTaskId()
    {
        return taskId;
    }
    public void setSettingsId(Long settingsId)
    {
        this.settingsId = settingsId;
    }

    public Long getSettingsId()
    {
        return settingsId;
    }
    public void setSupportMachineId(Long supportMachineId)
    {
        this.supportMachineId = supportMachineId;
    }

    public Long getSupportMachineId()
    {
        return supportMachineId;
    }
    public void setSupportMachineCode(String supportMachineCode)
    {
        this.supportMachineCode = supportMachineCode;
    }

    public String getSupportMachineCode()
    {
        return supportMachineCode;
    }
    public void setEmployeeId(Long employeeId)
    {
        this.employeeId = employeeId;
    }

    public Long getEmployeeId()
    {
        return employeeId;
    }
    public void setEmployeeCode(String employeeCode)
    {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeCode()
    {
        return employeeCode;
    }
    public void setBindFlag(Integer bindFlag)
    {
        this.bindFlag = bindFlag;
    }

    public Integer getBindFlag()
    {
        return bindFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("taskId", getTaskId())
                .append("settingsId", getSettingsId())
                .append("supportMachineId", getSupportMachineId())
                .append("supportMachineCode", getSupportMachineCode())
                .append("remark", getRemark())
                .append("employeeId", getEmployeeId())
                .append("employeeCode", getEmployeeCode())
                .append("bindFlag", getBindFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
