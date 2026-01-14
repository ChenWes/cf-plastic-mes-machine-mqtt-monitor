package com.cf.mes.domain;

import com.cf.common.annotation.Excel;
import com.cf.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

///import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
///import io.swagger.annotations.ApiModel;
///import io.swagger.annotations.ApiModelProperty;

/**
 * 加料/烤料任务对象 feeding_task
 *
 * @author wilfmao
 * @date 2026-01-13
 */
///@ApiModel(value = "加料/烤料任务")
public class FeedingTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 加料任务ID */
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "${comment}")
    private Long id;

    /** 加料批号 */
    @Excel(name = "加料批号")
    //@ApiModelProperty(value = "加料批号")
    private String batchNo;

    /** 机器ID */
    @Excel(name = "机器ID")
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "机器ID")
    private Long machineId;

    /** 状态: preparing-准备中,running-执行中,completed-完成,clear-清料,returned-退料,canceled-取消 */
    @Excel(name = "状态: preparing-准备中,running-执行中,completed-完成,clear-清料,returned-退料,canceled-取消")
    //@ApiModelProperty(value = "状态: preparing-准备中,running-执行中,completed-完成,clear-清料,returned-退料,canceled-取消")
    private String taskStatus;

    /** 任务类型：normal-正常，prepare-预烤料 */
    @Excel(name = "任务类型：normal-正常，prepare-预烤料")
    //@ApiModelProperty(value = "任务类型：normal-正常，prepare-预烤料")
    private String taskType;

    /** 模具编号 */
    @Excel(name = "模具编号")
    //@ApiModelProperty(value = "模具编号")
    private String mouldCode;

    /** 工单ID列表 */
    @Excel(name = "工单ID列表")
    //@ApiModelProperty(value = "工单ID列表")
    private String jobOrderIds;

    /** 开始时间 */
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    //@ApiModelProperty(value = "开始时间")
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
    public void setTaskType(String taskType)
    {
        this.taskType = taskType;
    }

    public String getTaskType()
    {
        return taskType;
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
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("batchNo", getBatchNo())
                .append("machineId", getMachineId())
                .append("taskStatus", getTaskStatus())
                .append("taskType", getTaskType())
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
