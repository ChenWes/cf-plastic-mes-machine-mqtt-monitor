package com.cf.mes.service.impl;

import com.cf.common.annotation.Lang;
import com.cf.common.constant.MessageCode;
import com.cf.common.core.token.ITokenService;
import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoKeyFieldName;
import com.cf.common.enums.LangInfoTableName;
import com.cf.common.exception.CustomException;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.ListUtil;
import com.cf.common.utils.MessageUtils;
import com.cf.common.utils.StringUtils;
import com.cf.mes.domain.Machine;
import com.cf.mes.domain.MachineType;
import com.cf.mes.mapper.MachineTypeMapper;
import com.cf.mes.service.IMachineService;
import com.cf.mes.service.IMachineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 机器类型业务接口实现
 * @Author ccw
 * @Date 2021/5/20
 */
@Service
@Lang(keyFieldName = LangInfoKeyFieldName.MACHINE_TYPE_ID_KEY_FILE_NAME, tableName = LangInfoTableName.MACHINE_TYPE_TABLE_NAME, columnNames = {LangInfoColumnName.MACHINE_TYPE_NAME_COLUMN_NAME})
public class MachineTypeServiceImpl implements IMachineTypeService {
    @Autowired
    private MachineTypeMapper machineTypeMapper;

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private IMachineService machineService;

    /**
     * 查询机器类型
     *
     * @param machineTypeId 机器类型ID
     * @return 机器类型
     */
    @Override
    public MachineType getMachineTypeById(Long machineTypeId) {
        return machineTypeMapper.getMachineTypeById(machineTypeId);
    }

    /**
     * 查询机器类型
     *
     * @param machineTypeCode
     * @return
     */
    @Override
    public MachineType getMachineTypeByCode(String machineTypeCode) {
        return machineTypeMapper.getMachineTypeByCode(machineTypeCode);
    }

    /**
     * 查询机器类型列表
     *
     * @param machineType 机器类型
     * @return 机器类型集合
     */
    @Override
    public List<MachineType> getMachineTypeList(MachineType machineType) {
        return machineTypeMapper.getMachineTypeList(machineType);
    }

    /**
     * 新增机器类型
     *
     * @param machineType 机器类型
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertMachineType(MachineType machineType) {
        //check data
        if (StringUtils.isEmpty(machineType.getMachineTypeCode())) {
            String msg = MessageUtils.message(MessageCode.MACHINE_TYPE_CODE_EMPTY);
            throw new CustomException(msg);
        }
        MachineType checkObj = machineTypeMapper.getMachineTypeByCode(machineType.getMachineTypeCode());
        if (checkObj != null) {
            String msg = MessageUtils.message(MessageCode.MACHINE_TYPE_CODE_EXISTS, checkObj.getMachineTypeCode());
            throw new CustomException(msg);
        }
        machineType.setCreateBy(tokenService.getUserCode());
        machineType.setCreateTime(DateUtils.getNowDate());
        return machineTypeMapper.insertMachineType(machineType);
    }

    /**
     * 修改机器类型
     *
     * @param machineType 机器类型
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateMachineType(MachineType machineType) {
        machineType.setUpdateBy(tokenService.getUserCode());
        machineType.setUpdateTime(DateUtils.getNowDate());
        return machineTypeMapper.updateMachineType(machineType);
    }

    @Override
    public List<MachineType> getMachineTypeListByIds(List<Long> machineTypeIds) {
        machineTypeIds = ListUtil.getDistinctList(machineTypeIds);
        if (CollectionUtils.isEmpty(machineTypeIds)) {
            return new ArrayList<>();
        }
        return machineTypeMapper.getMachineTypeListByIds(machineTypeIds);
    }
}
