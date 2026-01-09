package com.cf.mes.service;

import com.cf.mes.domain.ProductionTaskMtc;

import java.util.List;

/**
 * 生产任务关联模温机Service接口
 *
 * @author wilfmao
 * @date 2026-01-08
 */
public interface IProductionTaskMtcService {
    /**
     * 查询生产任务关联模温机
     *
     * @param id 生产任务关联模温机主键
     * @return 生产任务关联模温机
     */
    public ProductionTaskMtc getProductionTaskMtcById(Long id);

    /**
     * 根据ID列表，查询生产任务关联模温机列表
     *
     * @param ids 需要查询的生产任务关联模温机主键集合
     * @return 生产任务关联模温机集合
     */
    public List<ProductionTaskMtc> getProductionTaskMtcListByIds(List<Long> ids);

    /**
     * 查询生产任务关联模温机列表
     *
     * @param productionTaskMtc 生产任务关联模温机
     * @return 生产任务关联模温机集合
     */
    public List<ProductionTaskMtc> getProductionTaskMtcList(ProductionTaskMtc productionTaskMtc);

    /**
     * 新增生产任务关联模温机
     *
     * @param productionTaskMtc 生产任务关联模温机
     * @return 结果
     */
    public int insertProductionTaskMtc(ProductionTaskMtc productionTaskMtc);

    /**
     * 修改生产任务关联模温机
     *
     * @param productionTaskMtc 生产任务关联模温机
     * @return 结果
     */
    public int updateProductionTaskMtc(ProductionTaskMtc productionTaskMtc);

    /**
     * 批量删除生产任务关联模温机
     *
     * @param ids 需要删除的生产任务关联模温机主键集合
     * @return 结果
     */
    public int deleteProductionTaskMtcByIds(Long[] ids);

    /**
     * 删除生产任务关联模温机信息
     *
     * @param id 生产任务关联模温机主键
     * @return 结果
     */
    public int deleteProductionTaskMtcById(Long id);

    List<ProductionTaskMtc> getProductionTaskMtcByTaskId(Long taskId);

    List<ProductionTaskMtc> getProducingMouldTemperatureMachineList();

    List<ProductionTaskMtc> getProducingMouldTemperatureMachines(List<String> supportMachineCodes, List<Long> supportMachineIds);

    int unbindProductionTaskMtcs(Long taskId, List<Long> delIdList);

    ProductionTaskMtc getProducingMouldTemperatureMachine(String machineCode);
}
