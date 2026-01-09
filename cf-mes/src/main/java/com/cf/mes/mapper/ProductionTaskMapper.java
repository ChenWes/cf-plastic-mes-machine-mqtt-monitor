package com.cf.mes.mapper;

import com.cf.mes.domain.ProductionTask;

import java.util.List;

/**
 * 生产任务Mapper接口
 * 
 * @author wilfmao
 * @date 2026-01-08
 */
public interface ProductionTaskMapper 
{
    /**
     * 查询生产任务
     * 
     * @param id 生产任务主键
     * @return 生产任务
     */
    public ProductionTask getProductionTaskById(Long id);

    /**
     * 查询生产任务列表
     * 
     * @param productionTask 生产任务
     * @return 生产任务集合
     */
    public List<ProductionTask> getProductionTaskList(ProductionTask productionTask);

    /**
     * 根据ID列表，查询生产任务列表
     *
     * @param ids 需要查询的生产任务主键集合
     * @return 生产任务集合
     */
    public List<ProductionTask> getProductionTaskListByIds(List<Long> ids);

    /**
     * 新增生产任务
     * 
     * @param productionTask 生产任务
     * @return 结果
     */
    public int insertProductionTask(ProductionTask productionTask);

    /**
     * 修改生产任务
     * 
     * @param productionTask 生产任务
     * @return 结果
     */
    public int updateProductionTask(ProductionTask productionTask);

    /**
     * 删除生产任务
     * 
     * @param id 生产任务主键
     * @return 结果
     */
    public int deleteProductionTaskById(Long id);

    /**
     * 批量删除生产任务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProductionTaskByIds(Long[] ids);
}
