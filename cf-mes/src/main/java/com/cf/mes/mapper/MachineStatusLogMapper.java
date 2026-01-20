package com.cf.mes.mapper;

import com.cf.mes.domain.MachineStatusLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Description 机器状态日志数据接口
 * @Author WesChen
 * @Date 2021-07-01
 */
public interface MachineStatusLogMapper {

    List<MachineStatusLog> getMachineStatusLogList(MachineStatusLog machineStatusLog);

    MachineStatusLog getMachineStatusLogById(Long machineStatusLogId);

    int insertMachineStatusLog(MachineStatusLog machineStatusLog);

    MachineStatusLog getLatestMachineStatusLogByMachineId(Long machineId);

    /**
     * 根据机器id列表查询相关最后的日志信息
     *
     * @param machineIds id列表
     */
    List<MachineStatusLog> getLastMachineStatusLogListByMachineIds(@Param("machineIds") List<Long> machineIds);

    int updateMachineStatusLog(MachineStatusLog machineStatusLog);


    List<MachineStatusLog> getMachineStatusLogListByProcessDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    MachineStatusLog getLatestMachineStatusLogByMachineIdAndProduceId(@Param("machineId") Long machineId, @Param("produceId") Long produceId);

    List<MachineStatusLog> getMachineStatusLogListByMachineIdAndLogFromTime(@Param("machineId") Long machineId, @Param("logFromTime") Date logFromTime);

    List<MachineStatusLog> getMachineStatusLogListByMachineId(Long machineId);

    MachineStatusLog getFirstProductionLogOfCurrentProuction(Long machineId);
}
