package com.cf.system.mapper;

import com.cf.system.domain.FuncGuideFlow;

import java.util.List;

/**
 * 功能指引流程图Mapper接口
 *
 * @author wilfmao
 * @date 2024-11-21
 */
public interface FuncGuideFlowMapper {
    /**
     * 查询功能指引流程图
     *
     * @param guideFlowId 功能指引流程图主键
     * @return 功能指引流程图
     */
    public FuncGuideFlow getFuncGuideFlowByGuideFlowId(Long guideFlowId);

    /**
     * 查询功能指引流程图列表
     *
     * @param funcGuideFlow 功能指引流程图
     * @return 功能指引流程图集合
     */
    public List<FuncGuideFlow> getFuncGuideFlowList(FuncGuideFlow funcGuideFlow);

    /**
     * 根据ID列表，查询功能指引流程图列表
     *
     * @param guideFlowIds 需要查询的功能指引流程图主键集合
     * @return 功能指引流程图集合
     */
    public List<FuncGuideFlow> getFuncGuideFlowListByGuideFlowIds(List<Long> guideFlowIds);

    /**
     * 新增功能指引流程图
     *
     * @param funcGuideFlow 功能指引流程图
     * @return 结果
     */
    public int insertFuncGuideFlow(FuncGuideFlow funcGuideFlow);

    /**
     * 修改功能指引流程图
     *
     * @param funcGuideFlow 功能指引流程图
     * @return 结果
     */
    public int updateFuncGuideFlow(FuncGuideFlow funcGuideFlow);

    /**
     * 删除功能指引流程图
     *
     * @param guideFlowId 功能指引流程图主键
     * @return 结果
     */
    public int deleteFuncGuideFlowByGuideFlowId(Long guideFlowId);

    /**
     * 批量删除功能指引流程图
     *
     * @param guideFlowIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFuncGuideFlowByGuideFlowIds(Long[] guideFlowIds);
}
