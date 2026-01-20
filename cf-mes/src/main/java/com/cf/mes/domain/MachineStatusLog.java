package com.cf.mes.domain;


import com.cf.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class MachineStatusLog extends BaseEntity implements Comparable<MachineStatusLog> {

    private Long machineStatusLogId;

    private Long machineId;
    private Machine machineEntity;

    private Long employeeId;
    private Employee employeeEntity;

    private Long machineStatusId;
    private MachineStatus machineStatusEntity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date logFromTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date logEndTime;

    @Override
    public int compareTo(MachineStatusLog o) {
        int value1 = new Long(this.machineStatusLogId).intValue();
        int value2 = new Long(o.machineStatusLogId).intValue();

        return value2 - value1;//this-参数 降序
    }
}
