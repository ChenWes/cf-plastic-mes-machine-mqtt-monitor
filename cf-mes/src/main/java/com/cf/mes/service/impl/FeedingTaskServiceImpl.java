package com.cf.mes.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.SecurityUtils;
import com.cf.mes.controller.request.FeedingTaskDetailQryDto;
import com.cf.mes.domain.FeedingTask;
import com.cf.mes.domain.dto.DryingMachineParams;
import com.cf.mes.domain.dto.FeedingTaskDetailStatDto;
import com.cf.mes.domain.dto.MachineFeedingTaskDetailDto;
import com.cf.mes.mapper.FeedingTaskMapper;
import com.cf.mes.service.IFeedingTaskOrderDetailService;
import com.cf.mes.service.IFeedingTaskService;
import com.cf.mes.service.IMachineParamService;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
    public List<MachineFeedingTaskDetailDto> getMachineFeedingTaskDetailDtosByTaskId(Long taskId) {
        // 获取配方配置信息
        FeedingTaskDetailQryDto taskDetailQryDto = new FeedingTaskDetailQryDto();
        taskDetailQryDto.setTaskId(taskId);
        taskDetailQryDto.setTaskStatusList(Lists.newArrayList("running"));
        taskDetailQryDto.setLatestFlag(1);
        taskDetailQryDto.setResetFlag(0);
        List<MachineFeedingTaskDetailDto> taskDetailDtos = feedingTaskOrderDetailService.getMachineFeedingTaskDetailDtos(taskDetailQryDto);

        // 获取统计信息
        List<FeedingTaskDetailStatDto> taskDetailStatDtos = feedingTaskOrderDetailService.getMachineFeedingTaskDetailStatDtos(taskDetailQryDto);

        Date now = new Date();
        for (MachineFeedingTaskDetailDto detailDto : taskDetailDtos) {
            FeedingTaskDetailStatDto taskDetailStatDto = taskDetailStatDtos.stream()
                    .filter(r -> r.getMaterialId().equals(detailDto.getMaterialId())).findFirst().orElse(null);
            if (taskDetailStatDto != null) {
                detailDto.setFirstFeedingTime(taskDetailStatDto.getFirstFeedingTime());
                detailDto.setTotalFeedingWeight(taskDetailStatDto.getTotalFeedingWeight());
                long second = DateUtil.between(detailDto.getFirstFeedingTime(), now, DateUnit.SECOND);
                detailDto.setDryingDuration(second / 3600.0);
            }
            // 获取redis缓存信息
            DryingMachineParams dryingMachineParams = machineParamService.getDryingMachineParams(detailDto.getSupportMachineId(), detailDto.getSupportMachineCode());
            detailDto.setMachineParams(dryingMachineParams);
        }

        return taskDetailDtos;
    }
}
