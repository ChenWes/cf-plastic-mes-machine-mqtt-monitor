package com.cf.system.service.impl;

import com.cf.common.exception.CustomException;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.SecurityUtils;
import com.cf.common.utils.StringUtils;
import com.cf.system.domain.FuncGuideFlow;
import com.cf.system.mapper.FuncGuideFlowMapper;
import com.cf.system.service.IFuncGuideFlowService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 功能指引流程图Service业务层处理
 *
 * @author wilfmao
 * @date 2024-11-21
 */
@Service
public class FuncGuideFlowServiceImpl implements IFuncGuideFlowService {
    @Autowired
    private FuncGuideFlowMapper funcGuideFlowMapper;

    /**
     * 查询功能指引流程图
     *
     * @param guideFlowId 功能指引流程图主键
     * @return 功能指引流程图
     */
    @Override
    public FuncGuideFlow getFuncGuideFlowByGuideFlowId(Long guideFlowId) {
        return funcGuideFlowMapper.getFuncGuideFlowByGuideFlowId(guideFlowId);
    }

    /**
     * 查询功能指引流程图列表
     *
     * @param funcGuideFlow 功能指引流程图
     * @return 功能指引流程图
     */
    @Override
    public List<FuncGuideFlow> getFuncGuideFlowList(FuncGuideFlow funcGuideFlow) {
        return funcGuideFlowMapper.getFuncGuideFlowList(funcGuideFlow);
    }


    @Override
    public FuncGuideFlow getFuncGuideFlow(String funcGuideCode, String lang) {
        FuncGuideFlow query = new FuncGuideFlow();
        query.setFuncGuideCode(funcGuideCode);
        query.setLang(lang);
        List<FuncGuideFlow> funcGuideFlowList = getFuncGuideFlowList(query);
        if (CollectionUtils.isEmpty(funcGuideFlowList)) {
            return null;
        }
        return funcGuideFlowList.get(0);
    }

    /**
     * 根据ID列表，查询功能指引流程图列表
     *
     * @param guideFlowIds 需要查询的功能指引流程图主键集合
     * @return 功能指引流程图集合
     */
    @Override
    public List<FuncGuideFlow> getFuncGuideFlowListByGuideFlowIds(List<Long> guideFlowIds) {
        if (CollectionUtils.isEmpty(guideFlowIds)) {
            return new ArrayList<>();
        }
        guideFlowIds = guideFlowIds.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (guideFlowIds.isEmpty()) {
            return new ArrayList<>();
        }
        return funcGuideFlowMapper.getFuncGuideFlowListByGuideFlowIds(guideFlowIds);
    }

    /**
     * 新增功能指引流程图
     *
     * @param funcGuideFlow 功能指引流程图
     * @return 结果
     */
    @Override
    public int insertFuncGuideFlow(FuncGuideFlow funcGuideFlow) {
        if (StringUtils.isEmpty(funcGuideFlow.getFuncGuideCode()) || StringUtils.isEmpty(funcGuideFlow.getLang())) {
            throw new CustomException("Code or Lang cannot be empty!!");
        }
        FuncGuideFlow exist = getFuncGuideFlow(funcGuideFlow.getFuncGuideCode(), funcGuideFlow.getLang());
        if (exist != null) {
            throw new CustomException("Code is already exist!!");
        }
        funcGuideFlow.setCreateBy(SecurityUtils.getUserCode());
        funcGuideFlow.setCreateTime(DateUtils.getNowDate());
        return funcGuideFlowMapper.insertFuncGuideFlow(funcGuideFlow);
    }

    /**
     * 修改功能指引流程图
     *
     * @param funcGuideFlow 功能指引流程图
     * @return 结果
     */
    @Override
    public int updateFuncGuideFlow(FuncGuideFlow funcGuideFlow) {
        if (StringUtils.isEmpty(funcGuideFlow.getFuncGuideCode()) || StringUtils.isEmpty(funcGuideFlow.getLang())) {
            throw new CustomException("Code or Lang cannot be empty!!");
        }
        FuncGuideFlow exist = getFuncGuideFlow(funcGuideFlow.getFuncGuideCode(), funcGuideFlow.getLang());
        if (exist == null) {
            throw new CustomException("Code is Not exist!!");
        }
        if (!exist.getGuideFlowId().equals(funcGuideFlow.getGuideFlowId())) {
            throw new CustomException("Code is Duplicate!!");
        }
        funcGuideFlow.setUpdateBy(SecurityUtils.getUserCode());
        funcGuideFlow.setUpdateTime(DateUtils.getNowDate());
        return funcGuideFlowMapper.updateFuncGuideFlow(funcGuideFlow);
    }

    /**
     * 批量删除功能指引流程图
     *
     * @param guideFlowIds 需要删除的功能指引流程图主键
     * @return 结果
     */
    @Override
    public int deleteFuncGuideFlowByGuideFlowIds(Long[] guideFlowIds) {
        return funcGuideFlowMapper.deleteFuncGuideFlowByGuideFlowIds(guideFlowIds);
    }

    /**
     * 删除功能指引流程图信息
     *
     * @param guideFlowId 功能指引流程图主键
     * @return 结果
     */
    @Override
    public int deleteFuncGuideFlowByGuideFlowId(Long guideFlowId) {
        return funcGuideFlowMapper.deleteFuncGuideFlowByGuideFlowId(guideFlowId);
    }

    @Override
    public FuncGuideFlow getGuideFlowByCode(String funcGuideCode) {
        FuncGuideFlow query = new FuncGuideFlow();
        query.setFuncGuideCode(funcGuideCode);
        List<FuncGuideFlow> funcGuideFlowList = this.getFuncGuideFlowList(query);
        if (CollectionUtils.isEmpty(funcGuideFlowList)) {
            return null;
        }
        return funcGuideFlowList.get(0);
    }
}
