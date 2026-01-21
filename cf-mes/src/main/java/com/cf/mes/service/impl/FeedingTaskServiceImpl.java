package com.cf.mes.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.SecurityUtils;
import com.cf.mes.controller.request.FeedingTaskDetailQryDto;
import com.cf.mes.domain.FeedingTask;
import com.cf.mes.domain.MachineStatusLog;
import com.cf.mes.domain.dto.DryingMachineParams;
import com.cf.mes.domain.dto.FeedingTaskDetailStatDto;
import com.cf.mes.domain.dto.MachineFeedingTaskDetailDto;
import com.cf.mes.mapper.FeedingTaskMapper;
import com.cf.mes.mapper.MachineStatusLogMapper;
import com.cf.mes.service.IFeedingTaskOrderDetailService;
import com.cf.mes.service.IFeedingTaskService;
import com.cf.mes.service.IMachineParamService;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 加料/烤料任务Service业务层处理
 *
 * @author wilfmao
 * @date 2026-01-13
 */
@Service
public class FeedingTaskServiceImpl implements IFeedingTaskService {
    @Autowired
    private FeedingTaskMapper feedingTaskMapper;

    @Autowired
    private IFeedingTaskOrderDetailService feedingTaskOrderDetailService;

    @Autowired
    private IMachineParamService machineParamService;

    @Autowired
    private MachineStatusLogMapper machineStatusLogMapper;

    /**
     * 查询加料/烤料任务
     *
     * @param id 加料/烤料任务主键
     * @return 加料/烤料任务
     */
    @Override
    public FeedingTask getFeedingTaskById(Long id) {
        return feedingTaskMapper.getFeedingTaskById(id);
    }

    /**
     * 查询加料/烤料任务列表
     *
     * @param feedingTask 加料/烤料任务
     * @return 加料/烤料任务
     */
    @Override
    public List<FeedingTask> getFeedingTaskList(FeedingTask feedingTask) {
        return feedingTaskMapper.getFeedingTaskList(feedingTask);
    }

    /**
     * 根据ID列表，查询加料/烤料任务列表
     *
     * @param ids 需要查询的加料/烤料任务主键集合
     * @return 加料/烤料任务集合
     */
    @Override
    public List<FeedingTask> getFeedingTaskListByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        ids = ids.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        return feedingTaskMapper.getFeedingTaskListByIds(ids);
    }

    /**
     * 新增加料/烤料任务
     *
     * @param feedingTask 加料/烤料任务
     * @return 结果
     */
    @Override
    public int insertFeedingTask(FeedingTask feedingTask) {
        feedingTask.setCreateBy(SecurityUtils.getUserCode());
        feedingTask.setCreateTime(DateUtils.getNowDate());
        return feedingTaskMapper.insertFeedingTask(feedingTask);
    }

    /**
     * 修改加料/烤料任务
     *
     * @param feedingTask 加料/烤料任务
     * @return 结果
     */
    @Override
    public int updateFeedingTask(FeedingTask feedingTask) {
        feedingTask.setUpdateBy(SecurityUtils.getUserCode());
        feedingTask.setUpdateTime(DateUtils.getNowDate());
        return feedingTaskMapper.updateFeedingTask(feedingTask);
    }

    /**
     * 批量删除加料/烤料任务
     *
     * @param ids 需要删除的加料/烤料任务主键
     * @return 结果
     */
    @Override
    public int deleteFeedingTaskByIds(Long[] ids) {
        return feedingTaskMapper.deleteFeedingTaskByIds(ids);
    }

    /**
     * 删除加料/烤料任务信息
     *
     * @param id 加料/烤料任务主键
     * @return 结果
     */
    @Override
    public int deleteFeedingTaskById(Long id) {
        return feedingTaskMapper.deleteFeedingTaskById(id);
    }


    @Override
    public List<MachineFeedingTaskDetailDto> getMachineFeedingTaskDetailDtosByTaskId(Long machineId, Long taskId) {
        // 获取配方配置信息
        FeedingTaskDetailQryDto taskDetailQryDto = new FeedingTaskDetailQryDto();
        taskDetailQryDto.setTaskId(taskId);
        // taskDetailQryDto.setTaskStatusList(Lists.newArrayList("running"));
        taskDetailQryDto.setLatestFlag(1);
        taskDetailQryDto.setResetFlag(0);
        List<MachineFeedingTaskDetailDto> taskDetailDtos = feedingTaskOrderDetailService.getMachineFeedingTaskDetailDtos(taskDetailQryDto);

        // 获取统计信息，转换为Map便于快速查找 O(1)
        Map<Long, FeedingTaskDetailStatDto> statMap = feedingTaskOrderDetailService
                .getMachineFeedingTaskDetailStatDtos(taskDetailQryDto)
                .stream()
                .collect(Collectors.toMap(FeedingTaskDetailStatDto::getMaterialId, Function.identity(), (a, b) -> a));

        // 获取当次生产-首次切换机器到生产状态的日志记录
        MachineStatusLog machineStatusLog = machineStatusLogMapper.getFirstProductionLogOfCurrentProuction(machineId);
        Date firstProductionTime = machineStatusLog != null ? machineStatusLog.getLogFromTime() : null;

        Date now = new Date();
        for (MachineFeedingTaskDetailDto detailDto : taskDetailDtos) {

            detailDto.setFirstProductionTime(firstProductionTime);

            // 设置统计信息
            FeedingTaskDetailStatDto statDto = statMap.get(detailDto.getMaterialId());
            if (statDto != null) {
                populateStatInfo(detailDto, statDto, firstProductionTime, now);
            }

            // 获取redis缓存信息
            DryingMachineParams dryingMachineParams = machineParamService.getDryingMachineParams(detailDto.getSupportMachineId(), detailDto.getSupportMachineCode());
            detailDto.setMachineParams(dryingMachineParams);
        }

        return taskDetailDtos;
    }

    /**
     * 填充统计信息
     *
     * @param detailDto           明细信息
     * @param statDto             统计信息
     * @param firstProductionTime 当次生产-首次切换机器生产状态时间
     * @param now                 当前时间
     */
    private void populateStatInfo(MachineFeedingTaskDetailDto detailDto, FeedingTaskDetailStatDto statDto, Date firstProductionTime, Date now) {

        Date firstFeedingTime = statDto.getFirstFeedingTime();
        detailDto.setFirstFeedingTime(firstFeedingTime);
        detailDto.setTotalFeedingWeight(statDto.getTotalFeedingWeight());

        // 计算干燥时长基准时间：取首次生产时间和首次投料时间的较大值
        Date dryingBaseTime = (firstProductionTime != null && firstProductionTime.after(firstFeedingTime))
                ? firstProductionTime
                : now;

        // 干燥时长（小时）
        long dryingSeconds = DateUtil.between(firstFeedingTime, dryingBaseTime, DateUnit.SECOND);
        detailDto.setDryingDuration(dryingSeconds / 3600.0);

        // 总干燥时长（小时）
        long totalDryingSeconds = DateUtil.between(firstFeedingTime, now, DateUnit.SECOND);
        detailDto.setTotalDryingDuration(totalDryingSeconds / 3600.0);
    }
}
