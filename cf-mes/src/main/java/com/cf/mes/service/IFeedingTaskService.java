package com.cf.mes.service;

import com.cf.mes.domain.FeedingTask;
import com.cf.mes.domain.dto.MachineFeedingTaskDetailDto;

import java.util.List;

/**
 * 加料/烤料任务Service接口
 *
 * @author wilfmao
 * @date 2026-01-13
 */
public interface IFeedingTaskService {
    /**
     * 查询加料/烤料任务
     *
     * @param id 加料/烤料任务主键
     * @return 加料/烤料任务
     */
    public FeedingTask getFeedingTaskById(Long id);

    /**
     * 根据ID列表，查询加料/烤料任务列表
     *
     * @param ids 需要查询的加料/烤料任务主键集合
     * @return 加料/烤料任务集合
     */
    public List<FeedingTask> getFeedingTaskListByIds(List<Long> ids);

    /**
     * 查询加料/烤料任务列表
     *
     * @param feedingTask 加料/烤料任务
     * @return 加料/烤料任务集合
     */
    public List<FeedingTask> getFeedingTaskList(FeedingTask feedingTask);

    /**
     * 新增加料/烤料任务
     *
     * @param feedingTask 加料/烤料任务
     * @return 结果
     */
    public int insertFeedingTask(FeedingTask feedingTask);

    /**
     * 修改加料/烤料任务
     *
     * @param feedingTask 加料/烤料任务
     * @return 结果
     */
    public int updateFeedingTask(FeedingTask feedingTask);

    /**
     * 批量删除加料/烤料任务
     *
     * @param ids 需要删除的加料/烤料任务主键集合
     * @return 结果
     */
    public int deleteFeedingTaskByIds(Long[] ids);

    /**
     * 删除加料/烤料任务信息
     *
     * @param id 加料/烤料任务主键
     * @return 结果
     */
    public int deleteFeedingTaskById(Long id);

    List<MachineFeedingTaskDetailDto> getMachineFeedingTaskDetailDtosByTaskId(Long taskId);
}
