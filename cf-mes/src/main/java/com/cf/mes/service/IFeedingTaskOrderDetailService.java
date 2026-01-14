package com.cf.mes.service;

import com.cf.mes.controller.request.FeedingTaskDetailQryDto;
import com.cf.mes.domain.FeedingTaskOrderDetail;
import com.cf.mes.domain.dto.FeedingTaskDetailStatDto;
import com.cf.mes.domain.dto.MachineFeedingTaskDetailDto;

import java.util.List;

/**
 * 加料/烤料任务明细Service接口
 *
 * @author wilfmao
 * @date 2026-01-13
 */
public interface IFeedingTaskOrderDetailService {
    /**
     * 查询加料/烤料任务明细
     *
     * @param detailId 加料/烤料任务明细主键
     * @return 加料/烤料任务明细
     */
    public FeedingTaskOrderDetail getFeedingTaskOrderDetailByDetailId(Long detailId);

    /**
     * 根据ID列表，查询加料/烤料任务明细列表
     *
     * @param detailIds 需要查询的加料/烤料任务明细主键集合
     * @return 加料/烤料任务明细集合
     */
    public List<FeedingTaskOrderDetail> getFeedingTaskOrderDetailListByDetailIds(List<Long> detailIds);

    /**
     * 查询加料/烤料任务明细列表
     *
     * @param feedingTaskOrderDetail 加料/烤料任务明细
     * @return 加料/烤料任务明细集合
     */
    public List<FeedingTaskOrderDetail> getFeedingTaskOrderDetailList(FeedingTaskOrderDetail feedingTaskOrderDetail);

    /**
     * 新增加料/烤料任务明细
     *
     * @param feedingTaskOrderDetail 加料/烤料任务明细
     * @return 结果
     */
    public int insertFeedingTaskOrderDetail(FeedingTaskOrderDetail feedingTaskOrderDetail);

    /**
     * 修改加料/烤料任务明细
     *
     * @param feedingTaskOrderDetail 加料/烤料任务明细
     * @return 结果
     */
    public int updateFeedingTaskOrderDetail(FeedingTaskOrderDetail feedingTaskOrderDetail);

    /**
     * 批量删除加料/烤料任务明细
     *
     * @param detailIds 需要删除的加料/烤料任务明细主键集合
     * @return 结果
     */
    public int deleteFeedingTaskOrderDetailByDetailIds(Long[] detailIds);

    /**
     * 删除加料/烤料任务明细信息
     *
     * @param detailId 加料/烤料任务明细主键
     * @return 结果
     */
    public int deleteFeedingTaskOrderDetailByDetailId(Long detailId);

    FeedingTaskOrderDetail getRunningDryingMachine(String machineCode);

    List<MachineFeedingTaskDetailDto> getMachineFeedingTaskDetailDtos(FeedingTaskDetailQryDto taskDetailQryDto);

    List<FeedingTaskDetailStatDto> getMachineFeedingTaskDetailStatDtos(FeedingTaskDetailQryDto taskDetailQryDto);
}
