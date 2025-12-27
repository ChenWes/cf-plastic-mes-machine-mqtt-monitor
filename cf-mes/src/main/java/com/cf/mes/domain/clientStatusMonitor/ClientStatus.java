package com.cf.mes.domain.clientStatusMonitor;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author ccw
 * @Date 12/11/2021
 */
@Data
public class ClientStatus  {

    private String factoryId;
    private String workshopId;
    private String machineId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    private ClientInformation clientInformation;
}
