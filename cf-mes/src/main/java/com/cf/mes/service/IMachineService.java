package com.cf.mes.service;

import com.cf.mes.domain.Machine;
import com.cf.mes.domain.MachineFilter;
import com.cf.mes.domain.MachineRegistration;
import com.cf.mes.domain.dto.OrganInfoDto;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @Description 机器业务接口
 * @Author ccw
 * @Date 2021/5/19
 */
public interface IMachineService {
    /**
     * 查询机器
     *
     * @param machineId 机器ID
     * @return 机器
     */
    Machine getMachineById(Long machineId);


    Machine getSlimMachineById(Long machineId);

    List<Machine> getMachineByIds(Long[] machineIds);

    Machine getMachineByIdForJobOrderDetails(Long machineId);

    /**
     * 获取不同语言机器
     *
     * @param machine
     * @return
     */
    Machine getMachineWithDifferentLang(Machine machine);

    /**
     * 查询机器
     *
     * @param machineCode
     * @return
     */
    Machine getMachineByCode(String machineCode);

    /**
     * 使用机器注册码查询机器
     *
     * @param registrationCode
     * @return
     */
    Machine getMachineByRegistrationCode(String registrationCode);


    /**
     * 查询机器列表
     *
     * @param machine 机器
     * @return 机器集合
     */
    List<Machine> getMachineList(Machine machine);


    List<Machine> getMachineListByIds(List<Long> machineIds);

    List<Machine> getMachineListWithSingleByIds(List<Long> machineIds);

    List<Machine> getMachines(Machine machine);

    /**
     * 该方法为getMachineList()的优化版方法,性能比该方法为getMachineList好
     *
     * @param machine
     * @return
     */
    List<Machine> getMachineListWithClientStatus(Machine machine);

    List<Machine> getRegisterMachineList();

    List<Machine> getMachineListByFilter(MachineFilter machineFilter);


    /**
     * 查询机器列表插入至缓存中
     *
     * @return
     */
    void getMachineListIntoCache();

    /**
     * 根据车间ids查询机器信息列表
     *
     * @param workshopIds 车间ids
     * @return 机器信息集合
     */
    List<Machine> getMachineListByWorkshopIds(Long[] workshopIds);

    List<Machine> getMachineListByWorkshopId(Long workshopId);

    List<Machine> getMachineListByFactoryId(Long factoryId);

    /**
     * 根据机器类型ids查询机器信息列表
     *
     * @param machineTypeIds 机器类型ids
     * @return 机器信息集合
     */
    List<Machine> getMachineListByMachineTypeIds(Long[] machineTypeIds);


    List<Machine> getMachineListByMachineTypeId(Long machineTypeId);

    /**
     * 新增机器
     *
     * @param machine 机器
     * @return 结果
     */
    int insertMachine(Machine machine);

    /**
     * 修改机器
     *
     * @param machine 机器
     * @return 结果
     */
    int updateMachine(Machine machine);


    /**
     * 注册机器
     *
     * @param machine
     * @return
     */
    int registrationMachine(MachineRegistration machine) throws IOException, TimeoutException;

    /**
     * 删除机器
     *
     * @param machineId 机器ID
     * @return 结果
     */
    int deleteMachineById(Long machineId);

    /**
     * 批量删除机器
     *
     * @param machineIds 需要删除的数据ID
     * @return 结果
     */
    int deleteMachineByIds(Long[] machineIds);

    List<Machine> getReportMachineList(Long factoryId, Long workshopId, Long machineTypeId, List<Long> machineIds);

    OrganInfoDto selectOrganizationInfo(Long machineId);
}
