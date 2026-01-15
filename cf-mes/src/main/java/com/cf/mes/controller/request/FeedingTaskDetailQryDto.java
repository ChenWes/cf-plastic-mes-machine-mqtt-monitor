package com.cf.mes.controller.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author coder-ren
 * @date 2026/1/13 16:30
 */
@Data
public class FeedingTaskDetailQryDto implements Serializable {
    private Long taskId;
    private List<String> taskTypeList;
    private List<String> taskStatusList;
    private Integer latestFlag;
    private Integer resetFlag;
}
