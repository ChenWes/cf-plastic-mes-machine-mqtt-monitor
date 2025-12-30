package com.cf.mqtt.entity.ptl.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author coder-ren
 * @date 2025/3/3 09:22
 */
@Data
public class StationTagInfoDto implements Serializable {

    /**
     * 基站ID
     */
    @JSONField(name = "ID")
    public String ID = "";

    /**
     * Tag ID
     */
    @JSONField(name = "TagID")
    public String TagID = "";

}
