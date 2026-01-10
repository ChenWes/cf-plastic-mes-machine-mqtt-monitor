package com.cf.mes.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
///import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cf.common.annotation.Excel;
import com.cf.common.core.domain.BaseEntity;
///import io.swagger.annotations.ApiModel;
///import io.swagger.annotations.ApiModelProperty;

/**
 * 生产任务对象 production_task
 *
 * @author wilfmao
 * @date 2026-01-10
 */
///@ApiModel(value = "生产任务")
public class ProductionTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 生产任务ID */
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "${comment}")
    private Long id;

    /** 生产批号 */
    @Excel(name = "生产批号")
    //@ApiModelProperty(value = "生产批号")
    private String batchNo;

    /** 机器ID */
    @Excel(name = "机器ID")
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "机器ID")
    private Long machineId;

    /** 状态: Producing-生产中, Suspend-暂停, Completed-完成 */
    @Excel(name = "状态: Producing-生产中, Suspend-暂停, Completed-完成")
    //@ApiModelProperty(value = "状态: Producing-生产中, Suspend-暂停, Completed-完成")
    private String taskStatus;

    /** 模具编号 */
    @Excel(name = "模具编号")
    //@ApiModelProperty(value = "模具编号")
    private String mouldCode;

    /** 工单ID列表 */
    @Excel(name = "工单ID列表")
    //@ApiModelProperty(value = "工单ID列表")
    private String jobOrderIds;

    /** 起工时间 */
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Excel(name = "起工时间", width = 30, dateFormat = "yyyy-MM-dd")
    //@ApiModelProperty(value = "起工时间")
    private Date startDate;

    /** 结束时间 */
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    //@ApiModelProperty(value = "结束时间")
    private Date endDate;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setBatchNo(String batchNo)
    {
        this.batchNo = batchNo;
    }

    public String getBatchNo()
    {
        return batchNo;
    }
    public void setMachineId(Long machineId)
    {
        this.machineId = machineId;
    }

    public Long getMachineId()
    {
        return machineId;
    }
    public void setTaskStatus(String taskStatus)
    {
        this.taskStatus = taskStatus;
    }

    public String getTaskStatus()
    {
        return taskStatus;
    }
    public void setMouldCode(String mouldCode)
    {
        this.mouldCode = mouldCode;
    }

    public String getMouldCode()
    {
        return mouldCode;
    }
    public void setJobOrderIds(String jobOrderIds)
    {
        this.jobOrderIds = jobOrderIds;
    }

    public String getJobOrderIds()
    {
        return jobOrderIds;
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("batchNo", getBatchNo())
                .append("machineId", getMachineId())
                .append("taskStatus", getTaskStatus())
                .append("mouldCode", getMouldCode())
                .append("jobOrderIds", getJobOrderIds())
                .append("startDate", getStartDate())
                .append("endDate", getEndDate())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
