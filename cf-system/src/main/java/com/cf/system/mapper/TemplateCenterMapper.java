package com.cf.system.mapper;

import com.cf.system.domain.TemplateCenter;

import java.util.List;

/**
 * 模版中心Mapper接口
 *
 * @author wilfmao
 * @date 2024-10-24
 */
public interface TemplateCenterMapper {
    /**
     * 查询模版中心
     *
     * @param templateId 模版中心主键
     * @return 模版中心
     */
    public TemplateCenter getTemplateCenterByTemplateId(Long templateId);

    /**
     * 查询模版中心列表
     *
     * @param templateCenter 模版中心
     * @return 模版中心集合
     */
    public List<TemplateCenter> getTemplateCenterList(TemplateCenter templateCenter);

    /**
     * 根据ID列表，查询模版中心列表
     *
     * @param templateIds 需要查询的模版中心主键集合
     * @return 模版中心集合
     */
    public List<TemplateCenter> getTemplateCenterListByTemplateIds(List<Long> templateIds);

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
     * 删除模版中心
     *
     * @param templateId 模版中心主键
     * @return 结果
     */
    public int deleteTemplateCenterByTemplateId(Long templateId);

    /**
     * 批量删除模版中心
     *
     * @param templateIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTemplateCenterByTemplateIds(Long[] templateIds);

    int updateByUploadFileId(TemplateCenter templateCenter);
}
