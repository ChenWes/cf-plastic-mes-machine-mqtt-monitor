package com.cf.mes.service.impl;

import com.cf.common.annotation.Lang;
import com.cf.common.constant.MessageCode;
import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoKeyFieldName;
import com.cf.common.enums.LangInfoTableName;
import com.cf.common.exception.CustomException;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.ListUtil;
import com.cf.common.utils.MessageUtils;
import com.cf.common.utils.StringUtils;
import com.cf.mes.domain.Factory;
import com.cf.mes.domain.Machine;
import com.cf.mes.domain.Workshop;
import com.cf.mes.mapper.WorkshopMapper;
import com.cf.mes.service.IFactoryService;
import com.cf.mes.service.IMachineService;
import com.cf.mes.service.IWorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 车间信息Service业务层处理
 *
 * @author luorog
 */
@Service
@Lang(keyFieldName = LangInfoKeyFieldName.WORKSHOP_ID_KEY_FILE_NAME, tableName = LangInfoTableName.WORKSHOP_TABLE_NAME, columnNames = {LangInfoColumnName.WORKSHOP_NAME_COLUMN_NAME})
public class WorkshopServiceImpl implements IWorkshopService {
    @Autowired
    private WorkshopMapper workshopMapper;

    @Autowired
    private IFactoryService factoryService;

    @Autowired
    private IMachineService machineService;

    /**
     * 查询车间信息
     *
     * @param workshopId 车间信息ID
     * @return 车间信息
     */
    @Override
    @Transactional(readOnly = true)
    public Workshop getWorkshopById(Long workshopId) {

        Workshop workshopItem = workshopMapper.getWorkshopById(workshopId);
        if (workshopItem != null) {
            workshopItem.setFactoryEntity(factoryService.getFactoryById((workshopItem.getFactoryId())));
        }
        return workshopItem;
    }

    @Override
    public Workshop getSlimWorkshopById(Long workshopId) {
        return workshopMapper.getWorkshopById(workshopId);
    }

    @Override
    @Transactional(readOnly = true)
    public Workshop getWorkshopByCode(String workshopCode) {

        Workshop workshopItem = workshopMapper.getWorkshopByCode(workshopCode);
        if (workshopItem != null) {
            workshopItem.setFactoryEntity(factoryService.getFactoryById((workshopItem.getFactoryId())));
        }
        return workshopItem;
    }

    /**
     * 查询车间信息列表
     *
     * @param workshop 车间信息
     * @return 车间信息
     */
    @Override
    @Transactional(readOnly = true)
    public List<Workshop> getWorkshopList(Workshop workshop) {

        List<Workshop> workshopList = workshopMapper.getWorkshopList(workshop);
        for (Workshop workshopItem : workshopList) {
            workshopItem.setFactoryEntity(factoryService.getFactoryById((workshopItem.getFactoryId())));
        }

        return workshopList;
    }

    @Override
    public List<Workshop> getSlimWorkshopList(Workshop workshop) {
        return workshopMapper.getSlimWorkshopList(workshop);
    }

    @Override
    public List<Workshop> getWorkshopListByFactoryId(Long factoryId) {
        return workshopMapper.getWorkshopListByFactoryId(factoryId);
    }


    /**
     * 根据工厂ids查询车间信息列表
     *
     * @param factoryIds 工厂ids
     * @return 车间信息集合
     */
    @Override
    public List<Workshop> getWorkshopListByFactoryIds(Long[] factoryIds) {
        return workshopMapper.getWorkshopListByFactoryIds(factoryIds);
    }

    /**
     * 新增车间信息
     *
     * @param workshop 车间信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertWorkshop(Workshop workshop) {

        //check data
        if (StringUtils.isEmpty(workshop.getWorkshopCode())) {
            String msg = MessageUtils.message(MessageCode.WORKSHOP_WORKSHOP_CODE_EMPTY);
            throw new CustomException(msg);
        }
        Workshop checkObj = this.workshopMapper.getWorkshopByCode(workshop.getWorkshopCode());
        if (checkObj != null) {
            String msg = MessageUtils.message(MessageCode.WORKSHOP_WORKSHOP_CODE_EXISTS, checkObj.getWorkshopCode());
            throw new CustomException(msg);
        }

        workshop.setCreateTime(DateUtils.getNowDate());
        return workshopMapper.insertWorkshop(workshop);
    }

    /**
     * 修改车间信息
     *
     * @param workshop 车间信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateWorkshop(Workshop workshop) {
        workshop.setUpdateTime(DateUtils.getNowDate());
        return workshopMapper.updateWorkshop(workshop);
    }

    /**
     * 批量删除车间信息`
     *
     * @param workshopIds 需要删除的车间信息ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteWorkshopByIds(Long[] workshopIds) {
        List<Machine> machineList = machineService.getMachineListByWorkshopIds(workshopIds);
        if (!machineList.isEmpty()) {
            String msg = MessageUtils.message(MessageCode.WORKSHOP_WORKSHOP_NOT_DELETE);
            throw new CustomException(msg);
        }
        return workshopMapper.deleteWorkshopByIds(workshopIds);
    }

    /**
     * 删除车间信息信息
     *
     * @param workshopId 车间信息ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteWorkshopById(Long workshopId) {
        List<Machine> machineList = machineService.getMachineListByWorkshopIds(new Long[]{workshopId});
        if (!machineList.isEmpty()) {
            String msg = MessageUtils.message(MessageCode.WORKSHOP_WORKSHOP_NOT_DELETE);
            throw new CustomException(msg);
        }
        return workshopMapper.deleteWorkshopById(workshopId);
    }

    @Override
    public List<Workshop> getWorkshopListByIds(List<Long> workshopIds) {
        workshopIds = ListUtil.getDistinctList(workshopIds);
        if (CollectionUtils.isEmpty(workshopIds)) {
            return new ArrayList<>();
        }
        return workshopMapper.getWorkshopListByIds(workshopIds);
    }

    @Override
    public List<Workshop> getWorkshopWithFactoryListByIds(List<Long> workshopIds) {
        List<Workshop> workshopList = getWorkshopListByIds(workshopIds);
        if (CollectionUtils.isEmpty(workshopList)) {
            return new ArrayList<>();
        }
        List<Long> factoryIds = workshopList.stream().map(Workshop::getFactoryId).collect(Collectors.toList());
        List<Factory> factories = factoryService.getFactoryListByIds(factoryIds);
        Factory factory;
        for (Workshop workshop : workshopList) {
            factory = factories.stream().filter(r -> r.getFactoryId().equals(workshop.getFactoryId()))
                    .findFirst().orElse(null);
            if (factory != null) {
                workshop.setFactoryEntity(factory);
            }
        }
        return workshopList;
    }
}
