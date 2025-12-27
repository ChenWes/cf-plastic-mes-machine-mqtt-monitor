package com.cf.mes.mapper;

import com.cf.mes.domain.Machine;
import com.cf.mes.domain.MachineFilter;
import com.cf.mes.domain.MachineRegistration;
import com.cf.mes.domain.dto.OrganInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 机器数据接口
 * @Author ccw
 * @Date 2021/5/19
 */
public interface MachineMapper {
    /**
     * 查询机器
     *
     * @param machineId 机器ID
     * @return 机器
     */
    Machine getMachineById(Long machineId);

    List<Machine> getMachineByIds(Long[] machineIds);

    /**
     * 查询机器
     *
     * @param machineCode
     * @return
     */
    Machine getMachineByCode(String machineCode);

    /**
     * 通过机器注册码查询机器
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

    List<Machine> getMachineListByIds(@Param("machineIds") List<Long> machineIds);

    /**
     * 查询已注册机器列表
     * @return
     */
    List<Machine> getRegisterMachineList();

    /**
     * 通过查询条件
     * 可以通过工序ID来查询匹配的机器类型
     *
     * @param machineFilter
     * @return
     */
    List<Machine> getMachineListByFilter(MachineFilter machineFilter);

    /**
     * 根据车间ids查询机器信息列表
     *
     * @param workshopIds 车间ids
     * @return 机器信息集合
     */
    List<Machine> getMachineListByWorkshopIds(Long[] workshopIds);


    /**
     * 根据车间id查询机器信息列表
     *
     * @param workshopId 车间id
     * @return 机器信息集合
     */
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
    int registrationMachine(MachineRegistration machine);

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

    List<Machine> getReportMachineList(@Param("factoryId") Long factoryId,@Param("workshopId") Long workshopId,
                                       @Param("machineTypeId") Long machineTypeId, @Param("machineIds")  List<Long> machineIds);

    OrganInfoDto selectOrganizationInfo(Long machineId);
}