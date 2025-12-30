package com.cf.mqtt.entity.ptl;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用于定义单个 PTL (灯条)通信结果的数据信息
 */
@Data
public class TaskItemResult implements Serializable {

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
     * LED 灯的当前状态 [R,G,B]
     */
    @JSONField(name = "Colors")
    public List<LightColorProtocol> Colors = null;

    /**
     * 自定义-冗余 AP ID
     */
    @JSONField(name = "ID")
    public String ID = "";
}