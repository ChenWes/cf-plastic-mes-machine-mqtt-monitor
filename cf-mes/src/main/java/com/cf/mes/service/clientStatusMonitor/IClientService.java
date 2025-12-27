package com.cf.mes.service.clientStatusMonitor;

import com.cf.mes.domain.Machine;
import com.cf.mes.domain.MachineFilter;
import com.cf.mes.domain.MachineRegistration;
import com.cf.mes.domain.clientStatusMonitor.Client;

import java.util.List;

/**
 * @Description 客户端状态
 * @Author WesChen
 * @Date 2021-12-14
 */
public interface IClientService {

    Client getMachineById(Long machineId);

    List<Client> getMachineListByWorkshopId(Long workshopId);

    List<Client> getMachineListByFactoryId(Long factoryId);
}
