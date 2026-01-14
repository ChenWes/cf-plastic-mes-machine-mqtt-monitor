package com.cf.mes.domain.dto;

import com.cf.mes.domain.FeedingTaskOrderDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MachineFeedingTaskDetailDto extends FeedingTaskOrderDetail {
    private static final long serialVersionUID = 1L;

    /**
     * 首次加料时间
     */
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date firstFeedingTime;

    /**
     * 累计加料重量
     */
    private BigDecimal totalFeedingWeight;


    /**
     * 烤料时长 : （当前时间 - 首次加料时间 ）/ 3600
     */
    private Double dryingDuration;


    // 物料性质配置信息
    /**
     * 温度下限（摄氏度）
     */
    private BigDecimal minTemperature;

    /**
     * 温度上限（摄氏度）
     */
    private BigDecimal maxTemperature;

    /**
     * 时间下限（小时）
     */
    private BigDecimal minTime;

    /**
     * 时间上限（小时）
     */
    private BigDecimal maxTime;


    /**
     * 干燥机机器参数
     */
    private DryingMachineParams machineParams;

    /*
     * 判断是否在设定配置的温度范围中
     */
    private Boolean inSettingsTempRange;

    /**
     * 判断是否在机器设定的温度范围中
     */
    private Boolean inMachineTempRange;

    /*
     * 判断是否在设定配置时间范围中
     */
    private Boolean inSettingsTimeRange;


}
