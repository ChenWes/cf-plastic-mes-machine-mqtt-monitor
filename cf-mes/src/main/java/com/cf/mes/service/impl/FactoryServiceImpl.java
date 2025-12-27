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
import com.cf.mes.domain.Workshop;
import com.cf.mes.mapper.FactoryMapper;
import com.cf.mes.service.IFactoryService;
import com.cf.mes.service.IWorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 工厂信息Service业务层处理
 *
 * @author luorog
 */
@Service
@Lang(keyFieldName = LangInfoKeyFieldName.FACTORY_ID_KEY_FILE_NAME, tableName = LangInfoTableName.FACTORY_TABLE_NAME, columnNames = {LangInfoColumnName.FACTORY_NAME_COLUMN_NAME})
public class FactoryServiceImpl implements IFactoryService {
    @Autowired
    private FactoryMapper factoryMapper;

    @Autowired
    private IWorkshopService workshopService;

    /**
     * 查询工厂信息
     *
     * @param factoryId 工厂信息ID
     * @return 工厂信息
     */
    @Override
    public Factory getFactoryById(Long factoryId) {
        return factoryMapper.getFactoryById(factoryId);
    }

    @Override
    public Factory getFactoryWithDifferentLang(Factory factory) {
        return factory;
    }

    @Override
    public Factory getFactoryByCode(String factoryCode) {
        return factoryMapper.getFactoryByCode(factoryCode);
    }

    /**
     * 查询工厂信息列表
     *
     * @param factory 工厂信息
     * @return 工厂信息
     */
    @Override
    public List<Factory> getFactoryList(Factory factory) {
        return factoryMapper.getFactoryList(factory);
    }

    /**
     * 新增工厂信息
     *
     * @param factory 工厂信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertFactory(Factory factory) {

        //check data
        if (StringUtils.isEmpty(factory.getFactoryCode())) {
            String msg = MessageUtils.message(MessageCode.FACTORY_FACTORY_CODE_EMPTY);
            throw new CustomException(msg);
        }
        Factory checkObj = this.factoryMapper.getFactoryByCode(factory.getFactoryCode());
        if (checkObj != null) {
            String msg = MessageUtils.message(MessageCode.FACTORY_FACTORY_CODE_EXISTS, checkObj.getFactoryCode());
            throw new CustomException(msg);
        }
        factory.setCreateTime(DateUtils.getNowDate());
        return factoryMapper.insertFactory(factory);

    }

    /**
     * 修改工厂信息
     *
     * @param factory 工厂信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateFactory(Factory factory) {
        factory.setUpdateTime(DateUtils.getNowDate());
        return factoryMapper.updateFactory(factory);
    }

    /**
     * 批量删除工厂信息
     *
     * @param factoryIds 需要删除的工厂信息ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteFactoryByIds(Long[] factoryIds) {
        List<Workshop> workshopList = workshopService.getWorkshopListByFactoryIds(factoryIds);
        if (!workshopList.isEmpty()) {
            String msg = MessageUtils.message(MessageCode.FACTORY_FACTORY_NOT_DELETE);
            throw new CustomException(msg);
        }
        return factoryMapper.deleteFactoryByIds(factoryIds);
    }

    /**
     * 删除工厂信息信息
     *
     * @param factoryId 工厂信息ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteFactoryById(Long factoryId) {
        List<Workshop> workshopList = workshopService.getWorkshopListByFactoryIds(new Long[]{factoryId});
        if (!workshopList.isEmpty()) {
            String msg = MessageUtils.message(MessageCode.FACTORY_FACTORY_NOT_DELETE);
            throw new CustomException(msg);
        }
        return factoryMapper.deleteFactoryById(factoryId);
    }

    @Override
    public List<Factory> getFactoryListByIds(List<Long> factoryIds) {
        factoryIds = ListUtil.getDistinctList(factoryIds);
        if (CollectionUtils.isEmpty(factoryIds)) {
            return new ArrayList<>();
        }
        return factoryMapper.getFactoryListByIds(factoryIds);
    }
}
