package com.cf.mes.mapper.clientStatusMonitor;

import com.cf.mes.domain.Machine;
import com.cf.mes.domain.MachineFilter;
import com.cf.mes.domain.MachineRegistration;
import com.cf.mes.domain.clientStatusMonitor.Client;

import java.util.List;

/**
 * @Description 客户端接口数据接口
 * @Author ccw
 * @Date 2021/5/19
 */
public interface ClientMapper {

    Client getMachineById(Long machineId);

    List<Client> getMachineListByWorkshopId(Long workshopId);

    List<Client> getMachineListByFactoryId(Long factoryId);
}