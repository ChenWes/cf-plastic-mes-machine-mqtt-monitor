package com.cf.mes.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.cf.common.annotation.Excel;
import com.cf.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

///import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
///import io.swagger.annotations.ApiModel;
///import io.swagger.annotations.ApiModelProperty;

/**
 * 加料/烤料任务明细对象 feeding_task_order_detail
 *
 * @author wilfmao
 * @date 2026-01-13
 */
///@ApiModel(value = "加料/烤料任务明细")
public class FeedingTaskOrderDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 加料明细ID */
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "${comment}")
    private Long detailId;

    /** 加料任务ID */
    @Excel(name = "加料任务ID")
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "加料任务ID")
    private Long taskId;

    /** 物料ID */
    @Excel(name = "物料ID")
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "物料ID")
    private Long materialId;

    /** 物料编码 */
    @Excel(name = "物料编码")
    //@ApiModelProperty(value = "物料编码")
    private String materialCode;

    /** 物料批号 */
    @Excel(name = "物料批号")
    //@ApiModelProperty(value = "物料批号")
    private String materialBatchNo;

    /** 状态: preparing-准备中,running-执行中,completed-完成,clear-清料,returned-退料,canceled-取消 */
    @Excel(name = "状态: preparing-准备中,running-执行中,completed-完成,clear-清料,returned-退料,canceled-取消")
    //@ApiModelProperty(value = "状态: preparing-准备中,running-执行中,completed-完成,clear-清料,returned-退料,canceled-取消")
    private String taskStatus;

    /** 辅机器ID */
    @Excel(name = "辅机器ID")
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "辅机器ID")
    private Long supportMachineId;

    /** 辅机编号 */
    @Excel(name = "辅机编号")
    //@ApiModelProperty(value = "辅机编号")
    private String supportMachineCode;

    /** 加料时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "加料时间", width = 30, dateFormat = "yyyy-MM-dd")
    //@ApiModelProperty(value = "加料时间")
    private Date feedingTime;

    /** 加料重量（KG） */
    @Excel(name = "加料重量", readConverterExp = "K=G")
    //@ApiModelProperty(value = "加料重量")
    private BigDecimal feedingWeight;

    /** 加料序号 */
    @Excel(name = "加料序号")
    //@ApiModelProperty(value = "加料序号")
    private Integer feedingSeqNo;

    /** 操作员工ID */
    @Excel(name = "操作员工ID")
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "操作员工ID")
    private Long employeeId;

    /** 操作员工编号 */
    @Excel(name = "操作员工编号")
    //@ApiModelProperty(value = "操作员工编号")
    private String employeeCode;

    /** 是否最后添加：1 是，0 否 */
    @Excel(name = "是否最后添加：1 是，0 否")
    //@ApiModelProperty(value = "是否最后添加：1 是，0 否")
    private Integer latestFlag;

    public void setDetailId(Long detailId)
    {
        this.detailId = detailId;
    }

    public Long getDetailId()
    {
        return detailId;
    }
    public void setTaskId(Long taskId)
    {
        this.taskId = taskId;
    }

    public Long getTaskId()
    {
        return taskId;
    }
    public void setMaterialId(Long materialId)
    {
        this.materialId = materialId;
    }

    public Long getMaterialId()
    {
        return materialId;
    }
    public void setMaterialCode(String materialCode)
    {
        this.materialCode = materialCode;
    }

    public String getMaterialCode()
    {
        return materialCode;
    }
    public void setMaterialBatchNo(String materialBatchNo)
    {
        this.materialBatchNo = materialBatchNo;
    }

    public String getMaterialBatchNo()
    {
        return materialBatchNo;
    }
    public void setTaskStatus(String taskStatus)
    {
        this.taskStatus = taskStatus;
    }

    public String getTaskStatus()
    {
        return taskStatus;
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
    public void setFeedingTime(Date feedingTime)
    {
        this.feedingTime = feedingTime;
    }

    public Date getFeedingTime()
    {
        return feedingTime;
    }
    public void setFeedingWeight(BigDecimal feedingWeight)
    {
        this.feedingWeight = feedingWeight;
    }

    public BigDecimal getFeedingWeight()
    {
        return feedingWeight;
    }
    public void setFeedingSeqNo(Integer feedingSeqNo)
    {
        this.feedingSeqNo = feedingSeqNo;
    }

    public Integer getFeedingSeqNo()
    {
        return feedingSeqNo;
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
    public void setLatestFlag(Integer latestFlag)
    {
        this.latestFlag = latestFlag;
    }

    public Integer getLatestFlag()
    {
        return latestFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("detailId", getDetailId())
                .append("taskId", getTaskId())
                .append("materialId", getMaterialId())
                .append("materialCode", getMaterialCode())
                .append("materialBatchNo", getMaterialBatchNo())
                .append("taskStatus", getTaskStatus())
                .append("supportMachineId", getSupportMachineId())
                .append("supportMachineCode", getSupportMachineCode())
                .append("feedingTime", getFeedingTime())
                .append("feedingWeight", getFeedingWeight())
                .append("feedingSeqNo", getFeedingSeqNo())
                .append("remark", getRemark())
                .append("employeeId", getEmployeeId())
                .append("employeeCode", getEmployeeCode())
                .append("latestFlag", getLatestFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
