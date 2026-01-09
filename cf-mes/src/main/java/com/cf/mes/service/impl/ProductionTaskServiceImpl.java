package com.cf.mes.service.impl;

import com.cf.common.constant.JobOrderStatusConstants;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.SecurityUtils;
import com.cf.mes.domain.ProductionTask;
import com.cf.mes.domain.ProductionTaskMtc;
import com.cf.mes.mapper.ProductionTaskMapper;
import com.cf.mes.service.IProductionTaskMtcService;
import com.cf.mes.service.IProductionTaskService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 生产任务Service业务层处理
 *
 * @author wilfmao
 * @date 2026-01-08
 */
@Service
public class ProductionTaskServiceImpl implements IProductionTaskService {
    @Autowired
    private ProductionTaskMapper productionTaskMapper;

    @Autowired
    private IProductionTaskMtcService productionTaskMtcService;


    /**
     * 查询生产任务
     *
     * @param id 生产任务主键
     * @return 生产任务
     */
    @Override
    public ProductionTask getProductionTaskById(Long id) {
        return productionTaskMapper.getProductionTaskById(id);
    }

    /**
     * 查询生产任务列表
     *
     * @param productionTask 生产任务
     * @return 生产任务
     */
    @Override
    public List<ProductionTask> getProductionTaskList(ProductionTask productionTask) {
        return productionTaskMapper.getProductionTaskList(productionTask);
    }

    /**
     * 根据ID列表，查询生产任务列表
     *
     * @param ids 需要查询的生产任务主键集合
     * @return 生产任务集合
     */
    @Override
    public List<ProductionTask> getProductionTaskListByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        ids = ids.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        return productionTaskMapper.getProductionTaskListByIds(ids);
    }

    /**
     * 新增生产任务
     *
     * @param productionTask 生产任务
     * @return 结果
     */
    @Override
    public int insertProductionTask(ProductionTask productionTask) {
        productionTask.setCreateBy(SecurityUtils.getUserCode());
        productionTask.setCreateTime(DateUtils.getNowDate());
        return productionTaskMapper.insertProductionTask(productionTask);
    }

    /**
     * 修改生产任务
     *
     * @param productionTask 生产任务
     * @return 结果
     */
    @Override
    public int updateProductionTask(ProductionTask productionTask) {
        productionTask.setUpdateBy(SecurityUtils.getUserCode());
        productionTask.setUpdateTime(DateUtils.getNowDate());
        return productionTaskMapper.updateProductionTask(productionTask);
    }

    /**
     * 批量删除生产任务
     *
     * @param ids 需要删除的生产任务主键
     * @return 结果
     */
    @Override
    public int deleteProductionTaskByIds(Long[] ids) {
        return productionTaskMapper.deleteProductionTaskByIds(ids);
    }

    /**
     * 删除生产任务信息
     *
     * @param id 生产任务主键
     * @return 结果
     */
    @Override
    public int deleteProductionTaskById(Long id) {
        return productionTaskMapper.deleteProductionTaskById(id);
    }

    @Override
    public List<ProductionTask> getMachineProductionTasks(Long machineId, String taskStatus) {
        ProductionTask query = new ProductionTask();
        query.setMachineId(machineId);
        query.setTaskStatus(taskStatus);
        return getProductionTaskList(query);
    }


    @Override
    public List<ProductionTaskMtc> getBindMouldTemperatureMachines(Long machineId) {
        String taskStatus = JobOrderStatusConstants.JOB_ORDER_STATUS_PRODUCING;
        List<ProductionTask> productionTasks = getMachineProductionTasks(machineId, taskStatus);
        if (CollectionUtils.isEmpty(productionTasks)) {
            return Collections.emptyList();
        }
        Long taskId = productionTasks.get(0).getId();
        return productionTaskMtcService.getProductionTaskMtcByTaskId(taskId);
    }

}
