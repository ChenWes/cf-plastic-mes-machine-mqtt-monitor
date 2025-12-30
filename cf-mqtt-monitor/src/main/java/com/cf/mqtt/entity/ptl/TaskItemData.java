package com.cf.mqtt.entity.ptl;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TaskItemData implements Serializable {
    /**
     * Tag ID
     */
    @JSONField(name = "TagID")
    public String TagID = "";
    /**
     * Beep
     */
    @JSONField(name = "Beep")
    public Boolean Beep = false;
    /**
     * LED light flashing times
     */
    @JSONField(name = "Flashing")
    public Boolean Flashing = false;
    /**
     * Light color
     * LightColorProtocol->
     * public boolean R = false;
     * public boolean G = false;
     * public boolean B = false;
     */
    @JSONField(name = "Colors")
    public List<LightColorProtocol> Colors = null;
}
