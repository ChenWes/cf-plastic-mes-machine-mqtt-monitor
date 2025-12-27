package com.cf.mes.domain.clientStatusMonitor;


import lombok.Data;

@Data
public class ClientHardDrive {

    private String name;

    private double totalFreeSpace;

    private double totalSize;

    private double utilizationRate;
}
