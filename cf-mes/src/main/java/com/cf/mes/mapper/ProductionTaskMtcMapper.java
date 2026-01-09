package com.cf.mes.mapper;

import com.cf.mes.domain.ProductionTaskMtc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 生产任务关联模温机Mapper接口
 *
 * @author wilfmao
 * @date 2026-01-08
 */
public interface ProductionTaskMtcMapper {
    /**
     * 查询生产任务关联模温机
     *
     * @param id 生产任务关联模温机主键
     * @return 生产任务关联模温机
     */
    public ProductionTaskMtc getProductionTaskMtcById(Long id);

    /**
     * 查询生产任务关联模温机列表
     *
     * @param productionTaskMtc 生产任务关联模温机
     * @return 生产任务关联模温机集合
     */
    public List<ProductionTaskMtc> getProductionTaskMtcList(ProductionTaskMtc productionTaskMtc);

    /**
     * 根据ID列表，查询生产任务关联模温机列表
     *
     * @param ids 需要查询的生产任务关联模温机主键集合
     * @return 生产任务关联模温机集合
     */
    public List<ProductionTaskMtc> getProductionTaskMtcListByIds(List<Long> ids);

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
     * 删除生产任务关联模温机
     *
     * @param id 生产任务关联模温机主键
     * @return 结果
     */
    public int deleteProductionTaskMtcById(Long id);

    /**
     * 批量删除生产任务关联模温机
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProductionTaskMtcByIds(Long[] ids);

    List<ProductionTaskMtc> getProducingMouldTemperatureMachineList(ProductionTaskMtc productionTaskMtc);

    List<ProductionTaskMtc> getProducingMouldTemperatureMachines(@Param("supportMachineCodes") List<String> supportMachineCodes,
                                                                 @Param("supportMachineIds") List<Long> supportMachineIds);

    int unbindProductionTaskMtcs(@Param("taskId") Long taskId, @Param("idList") List<Long> idList);

    ProductionTaskMtc getProducingMouldTemperatureMachine(String machineCode);
}
