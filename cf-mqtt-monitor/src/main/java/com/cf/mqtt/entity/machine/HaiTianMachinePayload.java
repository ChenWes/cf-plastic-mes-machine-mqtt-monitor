package com.cf.mqtt.entity.machine;

import com.alibaba.fastjson.annotation.JSONField;
import com.cf.mqtt.fastjson.Iso8601DateDeserializer;
import com.cf.mqtt.fastjson.Iso8601DateSerializer;
import lombok.Data;

import java.util.Date;

/**
 * 干燥机数据包
 *
 * @author coder-ren
 * @date 2025/12/30 19:02
 */
@Data
public class HaiTianMachinePayload {
    @JSONField(name = "device_id")
    private String deviceId;

    @JSONField(name = "device_name")
    private String deviceName;

    private String name;
    private Object value;
    // 使用Object类型，因为value可能是数值或字符串
    private String quality;

    @JSONField(deserializeUsing = Iso8601DateDeserializer.class, serializeUsing = Iso8601DateSerializer.class)
    private Date timestamp;

    private String unit;
    private String description;
}
