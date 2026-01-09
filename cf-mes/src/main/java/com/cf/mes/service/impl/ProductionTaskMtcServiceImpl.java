package com.cf.mes.service.impl;

import com.cf.common.utils.DateUtils;
import com.cf.common.utils.SecurityUtils;
import com.cf.mes.domain.ProductionTaskMtc;
import com.cf.mes.mapper.ProductionTaskMtcMapper;
import com.cf.mes.service.IProductionTaskMtcService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 生产任务关联模温机Service业务层处理
 *
 * @author wilfmao
 * @date 2026-01-08
 */
@Service
public class ProductionTaskMtcServiceImpl implements IProductionTaskMtcService {
    @Autowired
    private ProductionTaskMtcMapper productionTaskMtcMapper;

    /**
     * 查询生产任务关联模温机
     *
     * @param id 生产任务关联模温机主键
     * @return 生产任务关联模温机
     */
    @Override
    public ProductionTaskMtc getProductionTaskMtcById(Long id) {
        return productionTaskMtcMapper.getProductionTaskMtcById(id);
    }

    /**
     * 查询生产任务关联模温机列表
     *
     * @param productionTaskMtc 生产任务关联模温机
     * @return 生产任务关联模温机
     */
    @Override
    public List<ProductionTaskMtc> getProductionTaskMtcList(ProductionTaskMtc productionTaskMtc) {
        return productionTaskMtcMapper.getProductionTaskMtcList(productionTaskMtc);
    }

    /**
     * 根据ID列表，查询生产任务关联模温机列表
     *
     * @param ids 需要查询的生产任务关联模温机主键集合
     * @return 生产任务关联模温机集合
     */
    @Override
    public List<ProductionTaskMtc> getProductionTaskMtcListByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        ids = ids.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        return productionTaskMtcMapper.getProductionTaskMtcListByIds(ids);
    }

    /**
     * 新增生产任务关联模温机
     *
     * @param productionTaskMtc 生产任务关联模温机
     * @return 结果
     */
    @Override
    public int insertProductionTaskMtc(ProductionTaskMtc productionTaskMtc) {
        productionTaskMtc.setCreateBy(SecurityUtils.getUserCode());
        productionTaskMtc.setCreateTime(DateUtils.getNowDate());
        return productionTaskMtcMapper.insertProductionTaskMtc(productionTaskMtc);
    }

    /**
     * 修改生产任务关联模温机
     *
     * @param productionTaskMtc 生产任务关联模温机
     * @return 结果
     */
    @Override
    public int updateProductionTaskMtc(ProductionTaskMtc productionTaskMtc) {
        productionTaskMtc.setUpdateBy(SecurityUtils.getUserCode());
        productionTaskMtc.setUpdateTime(DateUtils.getNowDate());
        return productionTaskMtcMapper.updateProductionTaskMtc(productionTaskMtc);
    }

    /**
     * 批量删除生产任务关联模温机
     *
     * @param ids 需要删除的生产任务关联模温机主键
     * @return 结果
     */
    @Override
    public int deleteProductionTaskMtcByIds(Long[] ids) {
        return productionTaskMtcMapper.deleteProductionTaskMtcByIds(ids);
    }

    /**
     * 删除生产任务关联模温机信息
     *
     * @param id 生产任务关联模温机主键
     * @return 结果
     */
    @Override
    public int deleteProductionTaskMtcById(Long id) {
        return productionTaskMtcMapper.deleteProductionTaskMtcById(id);
    }

    @Override
    public List<ProductionTaskMtc> getProductionTaskMtcByTaskId(Long taskId) {
        ProductionTaskMtc query = new ProductionTaskMtc();
        query.setTaskId(taskId);
        query.setBindFlag(1);
        return getProductionTaskMtcList(query);
    }

    @Override
    public List<ProductionTaskMtc> getProducingMouldTemperatureMachineList() {
        return productionTaskMtcMapper.getProducingMouldTemperatureMachineList(null);
    }

    @Override
    public List<ProductionTaskMtc> getProducingMouldTemperatureMachines(List<String> supportMachineCodes, List<Long> supportMachineIds) {
        return productionTaskMtcMapper.getProducingMouldTemperatureMachines(supportMachineCodes, supportMachineIds);
    }

    @Override
    public int unbindProductionTaskMtcs(Long taskId, List<Long> delIdList) {
        return productionTaskMtcMapper.unbindProductionTaskMtcs(taskId, delIdList);
    }

    @Override
    public ProductionTaskMtc getProducingMouldTemperatureMachine(String machineCode) {
        return productionTaskMtcMapper.getProducingMouldTemperatureMachine(machineCode);
    }

}
