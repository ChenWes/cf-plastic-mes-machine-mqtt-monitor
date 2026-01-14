package com.cf.mes.mapper;

import com.cf.mes.domain.FeedingTaskOrder;

import java.util.List;

/**
 * 加料/烤料任务-关联工单Mapper接口
 * 
 * @author wilfmao
 * @date 2026-01-13
 */
public interface FeedingTaskOrderMapper 
{
    /**
     * 查询加料/烤料任务-关联工单
     * 
     * @param id 加料/烤料任务-关联工单主键
     * @return 加料/烤料任务-关联工单
     */
    public FeedingTaskOrder getFeedingTaskOrderById(Long id);

    /**
     * 查询加料/烤料任务-关联工单列表
     * 
     * @param feedingTaskOrder 加料/烤料任务-关联工单
     * @return 加料/烤料任务-关联工单集合
     */
    public List<FeedingTaskOrder> getFeedingTaskOrderList(FeedingTaskOrder feedingTaskOrder);

    /**
     * 根据ID列表，查询加料/烤料任务-关联工单列表
     *
     * @param ids 需要查询的加料/烤料任务-关联工单主键集合
     * @return 加料/烤料任务-关联工单集合
     */
    public List<FeedingTaskOrder> getFeedingTaskOrderListByIds(List<Long> ids);

    /**
     * 新增加料/烤料任务-关联工单
     * 
     * @param feedingTaskOrder 加料/烤料任务-关联工单
     * @return 结果
     */
    public int insertFeedingTaskOrder(FeedingTaskOrder feedingTaskOrder);

    /**
     * 修改加料/烤料任务-关联工单
     * 
     * @param feedingTaskOrder 加料/烤料任务-关联工单
     * @return 结果
     */
    public int updateFeedingTaskOrder(FeedingTaskOrder feedingTaskOrder);

    /**
     * 删除加料/烤料任务-关联工单
     * 
     * @param id 加料/烤料任务-关联工单主键
     * @return 结果
     */
    public int deleteFeedingTaskOrderById(Long id);

    /**
     * 批量删除加料/烤料任务-关联工单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFeedingTaskOrderByIds(Long[] ids);
}
