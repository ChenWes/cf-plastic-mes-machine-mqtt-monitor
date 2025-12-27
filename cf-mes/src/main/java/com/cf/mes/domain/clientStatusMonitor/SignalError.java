package com.cf.mes.domain.clientStatusMonitor;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author WesChen
 * @Date 2021-12-23
 */
@Data
public class SignalError {

    private boolean flag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    private String duration;
}
