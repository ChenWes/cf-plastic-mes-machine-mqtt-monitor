package com.cf.mes.domain;

import com.cf.common.annotation.Excel;
import com.cf.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

///import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
///import io.swagger.annotations.ApiModel;
///import io.swagger.annotations.ApiModelProperty;

/**
 * 模具温度设定对象 mould_temperature_settings
 *
 * @author wilfmao
 * @date 2026-01-02
 */
///@ApiModel(value = "模具温度设定")
public class MouldTemperatureSettings extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 设定ID
     */
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "${comment}")
    private Long settingsId;

    /**
     * 模具ID
     */
    @Excel(name = "模具ID")
    //@JsonSerialize(using= ToStringSerializer.class)
    //@ApiModelProperty(value = "模具ID")
    private Long mouldId;

    /**
     * 模具编码
     */
    @Excel(name = "模具编码")
    //@ApiModelProperty(value = "模具编码")
    private String mouldCode;

    /**
     * 模具部件类型：口水板(Runner_Plate)、前模(A_Plate)、后模(B_Plate)
     */
    @Excel(name = "模具部件类型：口水板(Runner_Plate)、前模(A_Plate)、后模(B_Plate)")
    //@ApiModelProperty(value = "模具部件类型：口水板(Runner_Plate)、前模(A_Plate)、后模(B_Plate)")
    private String partType;

    /**
     * 位置编号
     */
    @Excel(name = "位置编号")
    //@ApiModelProperty(value = "位置编号")
    private String position;

    /**
     * 位置名称
     */
    @Excel(name = "位置名称")
    //@ApiModelProperty(value = "位置名称")
    private String positionName;

    /**
     * 温度下限（摄氏度）
     */
    @Excel(name = "温度下限", readConverterExp = "摄=氏度")
    //@ApiModelProperty(value = "温度下限")
    private BigDecimal minTemperature;

    /**
     * 温度上限（摄氏度）
     */
    @Excel(name = "温度上限", readConverterExp = "摄=氏度")
    //@ApiModelProperty(value = "温度上限")
    private BigDecimal maxTemperature;

    public void setSettingsId(Long settingsId) {
        this.settingsId = settingsId;
    }

    public Long getSettingsId() {
        return settingsId;
    }

    public void setMouldId(Long mouldId) {
        this.mouldId = mouldId;
    }

    public Long getMouldId() {
        return mouldId;
    }

    public void setMouldCode(String mouldCode) {
        this.mouldCode = mouldCode;
    }

    public String getMouldCode() {
        return mouldCode;
    }

    public void setPartType(String partType) {
        this.partType = partType;
    }

    public String getPartType() {
        return partType;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setMinTemperature(BigDecimal minTemperature) {
        this.minTemperature = minTemperature;
    }

    public BigDecimal getMinTemperature() {
        return minTemperature;
    }

    public void setMaxTemperature(BigDecimal maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public BigDecimal getMaxTemperature() {
        return maxTemperature;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("settingsId", getSettingsId())
                .append("mouldId", getMouldId())
                .append("mouldCode", getMouldCode())
                .append("partType", getPartType())
                .append("position", getPosition())
                .append("positionName", getPositionName())
                .append("minTemperature", getMinTemperature())
                .append("maxTemperature", getMaxTemperature())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
