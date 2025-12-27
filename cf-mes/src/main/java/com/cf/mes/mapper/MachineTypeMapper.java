package com.cf.mes.mapper;

import com.cf.mes.domain.MachineType;

import java.util.List;

/**
 * @Description 机器类型数据接口
 * @Author ccw
 * @Date 2021/5/20
 */
public interface MachineTypeMapper {
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

    /**
     * 删除机器类型
     *
     * @param machineTypeId 机器类型ID
     * @return 结果
     */
    int deleteMachineTypeById(Long machineTypeId);

    /**
     * 批量删除机器类型
     *
     * @param machineTypeIds 需要删除的数据ID
     * @return 结果
     */
    int deleteMachineTypeByIds(Long[] machineTypeIds);

    List<MachineType> getMachineTypeListByIds(List<Long> machineTypeIds);
}
