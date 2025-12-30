package com.cf.mqtt.entity.ptl.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 标签在线状态
 *
 * @author coder-ren
 * @date 2025/2/26 23:19
 */
@Data
public class TagOnLineStatusInfoDto implements Serializable {
    /**
     * Tag ID
     */
    @JSONField(name = "TagID")
    public String TagID = "";


    /**
     * 基站ID
     */
    @JSONField(name = "ID")
    public String ID = "";

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
