package com.cf.mes.service.impl;

import com.cf.common.utils.DateUtils;
import com.cf.common.utils.SecurityUtils;
import com.cf.mes.controller.request.FeedingTaskDetailQryDto;
import com.cf.mes.domain.FeedingTaskOrderDetail;
import com.cf.mes.domain.dto.FeedingTaskDetailStatDto;
import com.cf.mes.domain.dto.MachineFeedingTaskDetailDto;
import com.cf.mes.mapper.FeedingTaskOrderDetailMapper;
import com.cf.mes.service.IFeedingTaskOrderDetailService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 加料/烤料任务明细Service业务层处理
 *
 * @author wilfmao
 * @date 2026-01-13
 */
@Service
public class FeedingTaskOrderDetailServiceImpl implements IFeedingTaskOrderDetailService {
    @Autowired
    private FeedingTaskOrderDetailMapper feedingTaskOrderDetailMapper;

    /**
     * 查询加料/烤料任务明细
     *
     * @param detailId 加料/烤料任务明细主键
     * @return 加料/烤料任务明细
     */
    @Override
    public FeedingTaskOrderDetail getFeedingTaskOrderDetailByDetailId(Long detailId) {
        return feedingTaskOrderDetailMapper.getFeedingTaskOrderDetailByDetailId(detailId);
    }

    /**
     * 查询加料/烤料任务明细列表
     *
     * @param feedingTaskOrderDetail 加料/烤料任务明细
     * @return 加料/烤料任务明细
     */
    @Override
    public List<FeedingTaskOrderDetail> getFeedingTaskOrderDetailList(FeedingTaskOrderDetail feedingTaskOrderDetail) {
        return feedingTaskOrderDetailMapper.getFeedingTaskOrderDetailList(feedingTaskOrderDetail);
    }

    /**
     * 根据ID列表，查询加料/烤料任务明细列表
     *
     * @param detailIds 需要查询的加料/烤料任务明细主键集合
     * @return 加料/烤料任务明细集合
     */
    @Override
    public List<FeedingTaskOrderDetail> getFeedingTaskOrderDetailListByDetailIds(List<Long> detailIds) {
        if (CollectionUtils.isEmpty(detailIds)) {
            return new ArrayList<>();
        }
        detailIds = detailIds.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (detailIds.isEmpty()) {
            return new ArrayList<>();
        }
        return feedingTaskOrderDetailMapper.getFeedingTaskOrderDetailListByDetailIds(detailIds);
    }

    /**
     * 新增加料/烤料任务明细
     *
     * @param feedingTaskOrderDetail 加料/烤料任务明细
     * @return 结果
     */
    @Override
    public int insertFeedingTaskOrderDetail(FeedingTaskOrderDetail feedingTaskOrderDetail) {
        feedingTaskOrderDetail.setCreateBy(SecurityUtils.getUserCode());
        feedingTaskOrderDetail.setCreateTime(DateUtils.getNowDate());
        return feedingTaskOrderDetailMapper.insertFeedingTaskOrderDetail(feedingTaskOrderDetail);
    }

    /**
     * 修改加料/烤料任务明细
     *
     * @param feedingTaskOrderDetail 加料/烤料任务明细
     * @return 结果
     */
    @Override
    public int updateFeedingTaskOrderDetail(FeedingTaskOrderDetail feedingTaskOrderDetail) {
        feedingTaskOrderDetail.setUpdateBy(SecurityUtils.getUserCode());
        feedingTaskOrderDetail.setUpdateTime(DateUtils.getNowDate());
        return feedingTaskOrderDetailMapper.updateFeedingTaskOrderDetail(feedingTaskOrderDetail);
    }

    /**
     * 批量删除加料/烤料任务明细
     *
     * @param detailIds 需要删除的加料/烤料任务明细主键
     * @return 结果
     */
    @Override
    public int deleteFeedingTaskOrderDetailByDetailIds(Long[] detailIds) {
        return feedingTaskOrderDetailMapper.deleteFeedingTaskOrderDetailByDetailIds(detailIds);
    }

    /**
     * 删除加料/烤料任务明细信息
     *
     * @param detailId 加料/烤料任务明细主键
     * @return 结果
     */
    @Override
    public int deleteFeedingTaskOrderDetailByDetailId(Long detailId) {
        return feedingTaskOrderDetailMapper.deleteFeedingTaskOrderDetailByDetailId(detailId);
    }

    @Override
    public FeedingTaskOrderDetail getRunningDryingMachine(String machineCode) {
        return feedingTaskOrderDetailMapper.getRunningDryingMachine(machineCode);
    }

    @Override
    public List<MachineFeedingTaskDetailDto> getMachineFeedingTaskDetailDtos(FeedingTaskDetailQryDto qryDto) {
        return feedingTaskOrderDetailMapper.getMachineFeedingTaskDetailDtos(qryDto);
    }

    @Override
    public List<FeedingTaskDetailStatDto> getMachineFeedingTaskDetailStatDtos(FeedingTaskDetailQryDto taskDetailQryDto) {
        return feedingTaskOrderDetailMapper.getMachineFeedingTaskDetailStatDtos(taskDetailQryDto);
    }
}
