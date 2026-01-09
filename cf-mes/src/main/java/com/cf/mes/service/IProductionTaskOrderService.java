package com.cf.mes.service;

import com.cf.mes.domain.ProductionTaskOrder;

import java.util.List;

/**
 * 生产任务关联工单Service接口
 * 
 * @author wilfmao
 * @date 2026-01-08
 */
public interface IProductionTaskOrderService 
{
    /**
     * 查询生产任务关联工单
     * 
     * @param id 生产任务关联工单主键
     * @return 生产任务关联工单
     */
    public ProductionTaskOrder getProductionTaskOrderById(Long id);

    /**
     * 根据ID列表，查询生产任务关联工单列表
     *
     * @param ids 需要查询的生产任务关联工单主键集合
     * @return 生产任务关联工单集合
     */
    public List<ProductionTaskOrder> getProductionTaskOrderListByIds(List<Long> ids);

    /**
     * 查询生产任务关联工单列表
     * 
     * @param productionTaskOrder 生产任务关联工单
     * @return 生产任务关联工单集合
     */
    public List<ProductionTaskOrder> getProductionTaskOrderList(ProductionTaskOrder productionTaskOrder);

    /**
     * 新增生产任务关联工单
     * 
     * @param productionTaskOrder 生产任务关联工单
     * @return 结果
     */
    public int insertProductionTaskOrder(ProductionTaskOrder productionTaskOrder);

    /**
     * 修改生产任务关联工单
     * 
     * @param productionTaskOrder 生产任务关联工单
     * @return 结果
     */
    public int updateProductionTaskOrder(ProductionTaskOrder productionTaskOrder);

    /**
     * 批量删除生产任务关联工单
     * 
     * @param ids 需要删除的生产任务关联工单主键集合
     * @return 结果
     */
    public int deleteProductionTaskOrderByIds(Long[] ids);

    /**
     * 删除生产任务关联工单信息
     * 
     * @param id 生产任务关联工单主键
     * @return 结果
     */
    public int deleteProductionTaskOrderById(Long id);
}
