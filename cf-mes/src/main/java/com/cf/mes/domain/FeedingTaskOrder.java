package com.cf.mes.domain;

import com.alibaba.fastjson.annotation.JSONField;
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
 * 加料/烤料任务-关联工单对象 feeding_task_order
 * 
 * @author wilfmao
 * @date 2026-01-13
 */
///@ApiModel(value = "加料/烤料任务-关联工单")
public class FeedingTaskOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "${comment}")
    private Long id;

    /** 加料任务ID */
    @Excel(name = "加料任务ID")
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "加料任务ID")
    private Long taskId;

    /** 工单ID */
    @Excel(name = "工单ID")
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "工单ID")
    private Long orderId;

    /** 工单编号 */
    @Excel(name = "工单编号")
    //@ApiModelProperty(value = "工单编号")
    private String orderCode;

    /** 加单时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "加单时间", width = 30, dateFormat = "yyyy-MM-dd")
    //@ApiModelProperty(value = "加单时间")
    private Date additionTime;

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
    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setOrderCode(String orderCode) 
    {
        this.orderCode = orderCode;
    }

    public String getOrderCode() 
    {
        return orderCode;
    }
    public void setAdditionTime(Date additionTime) 
    {
        this.additionTime = additionTime;
    }

    public Date getAdditionTime() 
    {
        return additionTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskId", getTaskId())
            .append("orderId", getOrderId())
            .append("orderCode", getOrderCode())
            .append("additionTime", getAdditionTime())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
