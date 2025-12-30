package com.cf.mqtt.entity.ptl;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TaskResult implements Serializable {
    /**
     * AP ID
     */
    @JSONField(name = "ID")
    public String ID = "";

    /**
     * Total count
     */
    @JSONField(name = "TotalCount")
    public int TotalCount = -1;

    /**
     * Send count
     */
    @JSONField(name = "SendCount")
    public int SendCount = -1;

    /**
     * Task item results
     */
    @JSONField(name = "Results")
    public List<TaskItemResult> Results;
}