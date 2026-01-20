package com.cf.mes.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;
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
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date firstFeedingTime;

    /**
     * 累计加料重量
     */
    private BigDecimal totalFeedingWeight;


    /**
     * 烤料时长 : （当次生产的首次切换机器状态到生产状态的时间 - 首次加料时间 ）/ 3600
     */
    private Double dryingDuration;

    /**
     * 烤料总时长 : （当前时间 - 首次加料时间 ）/ 3600
     */
    private Double totalDryingDuration;

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


    /**
     * 当次生产-首次切换机器生产状态时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date firstProductionTime;

}
