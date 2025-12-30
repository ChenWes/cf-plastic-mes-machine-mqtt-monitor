package com.cf.mqtt.entity.ptl;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class LightColorProtocol implements Serializable {
    /**
     * Red LED light
     */
    @JSONField(name = "R")
    public boolean R = false;

    /**
     * Green LED light
     */
    @JSONField(name = "G")
    public boolean G = false;

    /**
     * Blue LED light
     */
    @JSONField(name = "B")
    public boolean B = false;
}