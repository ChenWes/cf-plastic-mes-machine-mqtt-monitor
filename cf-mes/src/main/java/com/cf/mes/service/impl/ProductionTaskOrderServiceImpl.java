package com.cf.mes.service.impl;

import com.cf.common.utils.DateUtils;
import com.cf.common.utils.SecurityUtils;
import com.cf.mes.domain.ProductionTaskOrder;
import com.cf.mes.mapper.ProductionTaskOrderMapper;
import com.cf.mes.service.IProductionTaskOrderService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 生产任务关联工单Service业务层处理
 *
 * @author wilfmao
 * @date 2026-01-08
 */
@Service
public class ProductionTaskOrderServiceImpl implements IProductionTaskOrderService {
    @Autowired
    private ProductionTaskOrderMapper productionTaskOrderMapper;

    /**
     * 查询生产任务关联工单
     *
     * @param id 生产任务关联工单主键
     * @return 生产任务关联工单
     */
    @Override
    public ProductionTaskOrder getProductionTaskOrderById(Long id) {
        return productionTaskOrderMapper.getProductionTaskOrderById(id);
    }

    /**
     * 查询生产任务关联工单列表
     *
     * @param productionTaskOrder 生产任务关联工单
     * @return 生产任务关联工单
     */
    @Override
    public List<ProductionTaskOrder> getProductionTaskOrderList(ProductionTaskOrder productionTaskOrder) {
        return productionTaskOrderMapper.getProductionTaskOrderList(productionTaskOrder);
    }

    /**
     * 根据ID列表，查询生产任务关联工单列表
     *
     * @param ids 需要查询的生产任务关联工单主键集合
     * @return 生产任务关联工单集合
     */
    @Override
    public List<ProductionTaskOrder> getProductionTaskOrderListByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
            ids = ids.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        return productionTaskOrderMapper.getProductionTaskOrderListByIds(ids);
    }

    /**
     * 新增生产任务关联工单
     *
     * @param productionTaskOrder 生产任务关联工单
     * @return 结果
     */
    @Override
    public int insertProductionTaskOrder(ProductionTaskOrder productionTaskOrder) {
                productionTaskOrder.setCreateBy(SecurityUtils.getUserCode());
                productionTaskOrder.setCreateTime(DateUtils.getNowDate());
            return productionTaskOrderMapper.insertProductionTaskOrder(productionTaskOrder);
    }

    /**
     * 修改生产任务关联工单
     *
     * @param productionTaskOrder 生产任务关联工单
     * @return 结果
     */
    @Override
    public int updateProductionTaskOrder(ProductionTaskOrder productionTaskOrder) {
                productionTaskOrder.setUpdateBy(SecurityUtils.getUserCode());
                productionTaskOrder.setUpdateTime(DateUtils.getNowDate());
        return productionTaskOrderMapper.updateProductionTaskOrder(productionTaskOrder);
    }

    /**
     * 批量删除生产任务关联工单
     *
     * @param ids 需要删除的生产任务关联工单主键
     * @return 结果
     */
    @Override
    public int deleteProductionTaskOrderByIds(Long[] ids) {
        return productionTaskOrderMapper.deleteProductionTaskOrderByIds(ids);
    }

    /**
     * 删除生产任务关联工单信息
     *
     * @param id 生产任务关联工单主键
     * @return 结果
     */
    @Override
    public int deleteProductionTaskOrderById(Long id) {
        return productionTaskOrderMapper.deleteProductionTaskOrderById(id);
    }
}
