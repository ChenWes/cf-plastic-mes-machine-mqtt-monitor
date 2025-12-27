package com.cf.system.service;

import com.cf.common.core.domain.ListRes;
import com.cf.system.controller.response.TemplateCenterVo;
import com.cf.system.domain.TemplateCenter;

import java.util.List;

/**
 * 模版中心Service接口
 *
 * @author wilfmao
 * @date 2024-10-24
 */
public interface ITemplateCenterService {
    /**
     * 查询模版中心
     *
     * @param templateId 模版中心主键
     * @return 模版中心
     */
    public TemplateCenter getTemplateCenterByTemplateId(Long templateId);

    /**
     * 根据ID列表，查询模版中心列表
     *
     * @param templateIds 需要查询的模版中心主键集合
     * @return 模版中心集合
     */
    public List<TemplateCenter> getTemplateCenterListByTemplateIds(List<Long> templateIds);

    /**
     * 查询模版中心列表
     *
     * @param templateCenter 模版中心
     * @return 模版中心集合
     */
    public List<TemplateCenter> getTemplateCenterList(TemplateCenter templateCenter);

    /**
     * 新增模版中心
     *
     * @param templateCenter 模版中心
     * @return 结果
     */
    public int insertTemplateCenter(TemplateCenter templateCenter);

    /**
     * 修改模版中心
     *
     * @param templateCenter 模版中心
     * @return 结果
     */
    public int updateTemplateCenter(TemplateCenter templateCenter);

    /**
     * 批量删除模版中心
     *
     * @param templateIds 需要删除的模版中心主键集合
     * @return 结果
     */
    public int deleteTemplateCenterByTemplateIds(Long[] templateIds);

    /**
     * 删除模版中心信息
     *
     * @param templateId 模版中心主键
     * @return 结果
     */
    public int deleteTemplateCenterByTemplateId(Long templateId);

    ListRes<TemplateCenterVo> getTemplateCenterVoList(TemplateCenter templateCenter);

    TemplateCenterVo getTemplateCenterVoByTemplateId(Long templateId);

    int updateByUploadFileId(String uploadFileId);
}
