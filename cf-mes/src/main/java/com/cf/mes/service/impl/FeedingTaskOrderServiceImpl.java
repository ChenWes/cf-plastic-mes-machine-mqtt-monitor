package com.cf.mes.service.impl;

import com.cf.common.utils.DateUtils;
import com.cf.common.utils.SecurityUtils;
import com.cf.mes.domain.FeedingTaskOrder;
import com.cf.mes.mapper.FeedingTaskOrderMapper;
import com.cf.mes.service.IFeedingTaskOrderService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 加料/烤料任务-关联工单Service业务层处理
 *
 * @author wilfmao
 * @date 2026-01-13
 */
@Service
public class FeedingTaskOrderServiceImpl implements IFeedingTaskOrderService {
    @Autowired
    private FeedingTaskOrderMapper feedingTaskOrderMapper;

    /**
     * 查询加料/烤料任务-关联工单
     *
     * @param id 加料/烤料任务-关联工单主键
     * @return 加料/烤料任务-关联工单
     */
    @Override
    public FeedingTaskOrder getFeedingTaskOrderById(Long id) {
        return feedingTaskOrderMapper.getFeedingTaskOrderById(id);
    }

    /**
     * 查询加料/烤料任务-关联工单列表
     *
     * @param feedingTaskOrder 加料/烤料任务-关联工单
     * @return 加料/烤料任务-关联工单
     */
    @Override
    public List<FeedingTaskOrder> getFeedingTaskOrderList(FeedingTaskOrder feedingTaskOrder) {
        return feedingTaskOrderMapper.getFeedingTaskOrderList(feedingTaskOrder);
    }

    /**
     * 根据ID列表，查询加料/烤料任务-关联工单列表
     *
     * @param ids 需要查询的加料/烤料任务-关联工单主键集合
     * @return 加料/烤料任务-关联工单集合
     */
    @Override
    public List<FeedingTaskOrder> getFeedingTaskOrderListByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        ids = ids.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        return feedingTaskOrderMapper.getFeedingTaskOrderListByIds(ids);
    }

    /**
     * 新增加料/烤料任务-关联工单
     *
     * @param feedingTaskOrder 加料/烤料任务-关联工单
     * @return 结果
     */
    @Override
    public int insertFeedingTaskOrder(FeedingTaskOrder feedingTaskOrder) {
        feedingTaskOrder.setCreateBy(SecurityUtils.getUserCode());
        feedingTaskOrder.setCreateTime(DateUtils.getNowDate());
        return feedingTaskOrderMapper.insertFeedingTaskOrder(feedingTaskOrder);
    }

    /**
     * 修改加料/烤料任务-关联工单
     *
     * @param feedingTaskOrder 加料/烤料任务-关联工单
     * @return 结果
     */
    @Override
    public int updateFeedingTaskOrder(FeedingTaskOrder feedingTaskOrder) {
        feedingTaskOrder.setUpdateBy(SecurityUtils.getUserCode());
        feedingTaskOrder.setUpdateTime(DateUtils.getNowDate());
        return feedingTaskOrderMapper.updateFeedingTaskOrder(feedingTaskOrder);
    }

    /**
     * 批量删除加料/烤料任务-关联工单
     *
     * @param ids 需要删除的加料/烤料任务-关联工单主键
     * @return 结果
     */
    @Override
    public int deleteFeedingTaskOrderByIds(Long[] ids) {
        return feedingTaskOrderMapper.deleteFeedingTaskOrderByIds(ids);
    }

    /**
     * 删除加料/烤料任务-关联工单信息
     *
     * @param id 加料/烤料任务-关联工单主键
     * @return 结果
     */
    @Override
    public int deleteFeedingTaskOrderById(Long id) {
        return feedingTaskOrderMapper.deleteFeedingTaskOrderById(id);
    }
}
