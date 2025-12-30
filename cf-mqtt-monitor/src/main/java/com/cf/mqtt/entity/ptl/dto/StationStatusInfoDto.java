package com.cf.mqtt.entity.ptl.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.cf.mqtt.entity.ptl.StationInfo;
import lombok.Data;

import java.util.Date;

/**
 * @author coder-ren
 * @date 2025/3/3 09:22
 */
@Data
public class StationStatusInfoDto extends StationInfo {
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
