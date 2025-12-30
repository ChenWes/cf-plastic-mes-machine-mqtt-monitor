package com.cf.mqtt.entity.ptl;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TaskData implements Serializable {
    /**
     * LED light flashing time, =Time*5 seconds
     */
    @JSONField(name = "Time") // 不序列化该字段
    public int Time;
    /**
     * Task items
     */
    @JSONField(name = "Items")
    public List<TaskItemData> Items = null;
}
