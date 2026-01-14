package com.cf.mes.mapper;

import com.cf.mes.domain.FeedingTask;

import java.util.List;

/**
 * 加料/烤料任务Mapper接口
 * 
 * @author wilfmao
 * @date 2026-01-13
 */
public interface FeedingTaskMapper 
{
    /**
     * 查询加料/烤料任务
     * 
     * @param id 加料/烤料任务主键
     * @return 加料/烤料任务
     */
    public FeedingTask getFeedingTaskById(Long id);

    /**
     * 查询加料/烤料任务列表
     * 
     * @param feedingTask 加料/烤料任务
     * @return 加料/烤料任务集合
     */
    public List<FeedingTask> getFeedingTaskList(FeedingTask feedingTask);

    /**
     * 根据ID列表，查询加料/烤料任务列表
     *
     * @param ids 需要查询的加料/烤料任务主键集合
     * @return 加料/烤料任务集合
     */
    public List<FeedingTask> getFeedingTaskListByIds(List<Long> ids);

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
     * 删除加料/烤料任务
     * 
     * @param id 加料/烤料任务主键
     * @return 结果
     */
    public int deleteFeedingTaskById(Long id);

    /**
     * 批量删除加料/烤料任务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFeedingTaskByIds(Long[] ids);
}
