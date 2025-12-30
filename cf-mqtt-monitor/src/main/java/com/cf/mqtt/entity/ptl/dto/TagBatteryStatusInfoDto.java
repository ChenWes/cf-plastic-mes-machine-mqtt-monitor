package com.cf.mqtt.entity.ptl.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 标签电池状态
 * @author coder-ren
 * @date 2025/2/26 23:19
 */
@Data
public class TagBatteryStatusInfoDto implements Serializable {
    /**
     * Tag ID
     */
    @JSONField(name = "TagID")
    public String TagID = "";

    /**
     * Version
     */
    @JSONField(name = "Version")
    public String Version = "";

    /**
     * 0xFD 按键返回，0xFE 通信返回，0xFF 心跳返回
     */
    @JSONField(name = "ResultType")
    public int ResultType = 0;

    /**
     * 发送的射频功率
     */
    @JSONField(name = "RfPowerSend")
    public int RfPowerSend = 0;

    /**
     * 接收的射频功率
     */
    @JSONField(name = "RfPowerRecv")
    public int RfPowerRecv = 0;

    /**
     * 电池电量，电压 V，0 表示无数据，需要除以 10
     */
    @JSONField(name = "Battery")
    public int Battery = -1;

    /**
     * 上报时间
     */
    @JSONField(name = "UploadTime")
    public Date UploadTime;

    /**
     * 上报时间戳
     */
    @JSONField(name = "UploadTimestamp")
    public Long UploadTimestamp;

}
