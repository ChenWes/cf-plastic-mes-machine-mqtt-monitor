package com.cf.mes.service;

import com.cf.mes.domain.MachineType;

import java.util.List;

/**
 * @Description 机器类型业务接口
 * @Author ccw
 * @Date 2021/5/20
 */
public interface IMachineTypeService {
    /**
     * 查询机器类型
     *
     * @param machineTypeId 机器类型ID
     * @return 机器类型
     */
    MachineType getMachineTypeById(Long machineTypeId);

    /**
     * 查询机器类型
     *
     * @param machineTypeCode
     * @return
     */
    MachineType getMachineTypeByCode(String machineTypeCode);


    /**
     * 查询机器类型列表
     *
     * @param machineType 机器类型
     * @return 机器类型集合
     */
    List<MachineType> getMachineTypeList(MachineType machineType);

    /**
     * 新增机器类型
     *
     * @param machineType 机器类型
     * @return 结果
     */
    int insertMachineType(MachineType machineType);

    /**
     * 修改机器类型
     *
     * @param machineType 机器类型
     * @return 结果
     */
    int updateMachineType(MachineType machineType);

    List<MachineType> getMachineTypeListByIds(List<Long> machineTypeIds);
}
