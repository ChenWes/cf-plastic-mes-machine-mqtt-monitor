package com.cf.mes.domain.clientStatusMonitor;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ClientInformation {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startupTime;

    private String usageTime;

    private double cpuUtilization;

    private double cpuTemperature;

    private double memoryUtilization;

    private int signalType;

    private int connectStatus;

    private ClientHardDrive[] driveList;

    private SignalError signalErrorEntity;
}
