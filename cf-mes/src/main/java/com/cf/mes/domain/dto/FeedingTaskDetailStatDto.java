package com.cf.mes.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author coder-ren
 * @date 2026/1/13 16:30
 */
@Data
public class FeedingTaskDetailStatDto implements Serializable {

    private Long materialId;
    /**
     * 首次加料时间
     */
    private Date firstFeedingTime;

    /**
     * 累计加料重量
     */
    private BigDecimal totalFeedingWeight;


}
