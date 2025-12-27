package com.cf.system.service;

import com.cf.system.domain.FuncGuideFlow;

import java.util.List;

/**
 * 功能指引流程图Service接口
 * 
 * @author wilfmao
 * @date 2024-11-21
 */
public interface IFuncGuideFlowService 
{
    /**
     * 查询功能指引流程图
     * 
     * @param guideFlowId 功能指引流程图主键
     * @return 功能指引流程图
     */
    public FuncGuideFlow getFuncGuideFlowByGuideFlowId(Long guideFlowId);

    FuncGuideFlow getFuncGuideFlow(String funcGuideCode, String lang);

    /**
     * 根据ID列表，查询功能指引流程图列表
     *
     * @param guideFlowIds 需要查询的功能指引流程图主键集合
     * @return 功能指引流程图集合
     */
    public List<FuncGuideFlow> getFuncGuideFlowListByGuideFlowIds(List<Long> guideFlowIds);

    /**
     * 查询功能指引流程图列表
     * 
     * @param funcGuideFlow 功能指引流程图
     * @return 功能指引流程图集合
     */
    public List<FuncGuideFlow> getFuncGuideFlowList(FuncGuideFlow funcGuideFlow);

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
     * 批量删除功能指引流程图
     * 
     * @param guideFlowIds 需要删除的功能指引流程图主键集合
     * @return 结果
     */
    public int deleteFuncGuideFlowByGuideFlowIds(Long[] guideFlowIds);

    /**
     * 删除功能指引流程图信息
     * 
     * @param guideFlowId 功能指引流程图主键
     * @return 结果
     */
    public int deleteFuncGuideFlowByGuideFlowId(Long guideFlowId);

    FuncGuideFlow getGuideFlowByCode(String funcGuideCode);
}
