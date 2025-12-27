package com.cf.system.service.impl;

import com.cf.common.core.domain.ListRes;
import com.cf.common.core.domain.entity.SysDic;
import com.cf.common.core.minio.IMinIoService;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.SecurityUtils;
import com.cf.common.utils.StringUtils;
import com.cf.system.controller.convert.SysDicConvert;
import com.cf.system.controller.convert.TemplateCenterConvert;
import com.cf.system.controller.response.TemplateCenterVo;
import com.cf.system.domain.TemplateCenter;
import com.cf.system.mapper.TemplateCenterMapper;
import com.cf.system.service.ISysDicService;
import com.cf.system.service.ITemplateCenterService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 模版中心Service业务层处理
 *
 * @author wilfmao
 * @date 2024-10-24
 */
@Service
public class TemplateCenterServiceImpl implements ITemplateCenterService {
    @Autowired
    private TemplateCenterMapper templateCenterMapper;

    @Autowired
    private ISysDicService sysDicService;

    @Autowired
    private IMinIoService minIoService;

    /**
     * 查询模版中心
     *
     * @param templateId 模版中心主键
     * @return 模版中心
     */
    @Override
    public TemplateCenter getTemplateCenterByTemplateId(Long templateId) {
        return templateCenterMapper.getTemplateCenterByTemplateId(templateId);
    }

    /**
     * 查询模版中心列表
     *
     * @param templateCenter 模版中心
     * @return 模版中心
     */
    @Override
    public List<TemplateCenter> getTemplateCenterList(TemplateCenter templateCenter) {
        return templateCenterMapper.getTemplateCenterList(templateCenter);
    }

    /**
     * 根据ID列表，查询模版中心列表
     *
     * @param templateIds 需要查询的模版中心主键集合
     * @return 模版中心集合
     */
    @Override
    public List<TemplateCenter> getTemplateCenterListByTemplateIds(List<Long> templateIds) {
        if (CollectionUtils.isEmpty(templateIds)) {
            return new ArrayList<>();
        }
        templateIds = templateIds.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (templateIds.isEmpty()) {
            return new ArrayList<>();
        }
        return templateCenterMapper.getTemplateCenterListByTemplateIds(templateIds);
    }

    /**
     * 新增模版中心
     *
     * @param templateCenter 模版中心
     * @return 结果
     */
    @Override
    public int insertTemplateCenter(TemplateCenter templateCenter) {
        templateCenter.setCreateBy(SecurityUtils.getUserCode());
        templateCenter.setCreateTime(DateUtils.getNowDate());
        return templateCenterMapper.insertTemplateCenter(templateCenter);
    }

    /**
     * 修改模版中心
     *
     * @param templateCenter 模版中心
     * @return 结果
     */
    @Override
    public int updateTemplateCenter(TemplateCenter templateCenter) {
        templateCenter.setUpdateBy(SecurityUtils.getUserCode());
        templateCenter.setUpdateTime(DateUtils.getNowDate());
        return templateCenterMapper.updateTemplateCenter(templateCenter);
    }

    /**
     * 批量删除模版中心
     *
     * @param templateIds 需要删除的模版中心主键
     * @return 结果
     */
    @Override
    public int deleteTemplateCenterByTemplateIds(Long[] templateIds) {
        // 删除 文件服务器文件
        return templateCenterMapper.deleteTemplateCenterByTemplateIds(templateIds);
    }

    /**
     * 删除模版中心信息
     *
     * @param templateId 模版中心主键
     * @return 结果
     */
    @Override
    public int deleteTemplateCenterByTemplateId(Long templateId) {
        TemplateCenter templateCenter = getTemplateCenterByTemplateId(templateId);
        if (templateCenter == null) {
            return 0;
        }
        // 删除记录
        int res = templateCenterMapper.deleteTemplateCenterByTemplateId(templateId);

        String uploadFileId = templateCenter.getUploadFileId();
        // 删除附件信息
        if (StringUtils.isNotEmpty(uploadFileId)) {
            minIoService.remove(uploadFileId);
        }

        return res;
    }

    @Override
    public ListRes<TemplateCenterVo> getTemplateCenterVoList(TemplateCenter templateCenter) {
        List<TemplateCenter> list = this.getTemplateCenterList(templateCenter);
        if (CollectionUtils.isEmpty(list)) {
            return new ListRes<>(new ArrayList<>(), 0);
        }
        // 获取模版类型字典信息
        List<SysDic> sysDicList = sysDicService.getSysDicListByDicType("TemplateCenterTemplateType");
        // 封装返回数据
        List<TemplateCenterVo> voList = new ArrayList<>();
        for (TemplateCenter data : list) {
            if (data == null) {
                continue;
            }
            TemplateCenterVo vo = TemplateCenterConvert.toTemplateCenterVo(data);
            sysDicList.stream().filter(r -> r.getDicCode().equals(data.getTemplateTypeCode()))
                    .findFirst().ifPresent(sysDic -> vo.setTemplateTypeEntity(SysDicConvert.toSysDicVo(sysDic)));
            voList.add(vo);
        }
        return new ListRes(voList, (int) new PageInfo(list).getTotal());
    }

    private TemplateCenterVo toTemplateCenterVo(TemplateCenter templateCenter) {
        if (templateCenter == null) {
            return null;
        }

        TemplateCenterVo templateCenterVo = new TemplateCenterVo();

        templateCenterVo.setTemplateId(templateCenter.getTemplateId());
        templateCenterVo.setPlatformCode(templateCenter.getPlatformCode());
        templateCenterVo.setSystemCode(templateCenter.getSystemCode());
        templateCenterVo.setModuleCode(templateCenter.getModuleCode());
        templateCenterVo.setTemplateTypeCode(templateCenter.getTemplateTypeCode());
        templateCenterVo.setTemplateName(templateCenter.getTemplateName());
        templateCenterVo.setUploadFileId(templateCenter.getUploadFileId());
        templateCenterVo.setOriginalFileName(templateCenter.getOriginalFileName());
        templateCenterVo.setFileSize(templateCenter.getFileSize());
        templateCenterVo.setFileType(templateCenter.getFileType());
        templateCenterVo.setLang(templateCenter.getLang());
        templateCenterVo.setTag(templateCenter.getTag());
        templateCenterVo.setRemark(templateCenter.getRemark());

        return templateCenterVo;
    }

    @Override
    public TemplateCenterVo getTemplateCenterVoByTemplateId(Long templateId) {
        TemplateCenter data = getTemplateCenterByTemplateId(templateId);
        if (data == null) {
            return null;
        }
        TemplateCenterVo vo = TemplateCenterConvert.toTemplateCenterVo(data);
        SysDic query = new SysDic();
        query.setDicType("TemplateCenterTemplateType");
        query.setDicCode(data.getTemplateTypeCode());
        List<SysDic> sysDicList = sysDicService.getSysDicList(query);
        sysDicList.stream().filter(r -> r.getDicCode().equals(data.getTemplateTypeCode()))
                .findFirst().ifPresent(sysDic -> vo.setTemplateTypeEntity(SysDicConvert.toSysDicVo(sysDic)));
        return vo;
    }

    @Override
    public int updateByUploadFileId(String uploadFileId) {
        TemplateCenter query = new TemplateCenter();
        query.setUploadFileId(uploadFileId);
        List<TemplateCenter> list = getTemplateCenterList(query);
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        int count = 0;
        for (TemplateCenter templateCenter : list) {
            templateCenter.setUploadFileId("");
            templateCenter.setOriginalFileName("");
            templateCenter.setFileSize(0L);
            templateCenter.setFileType("");
            templateCenter.setUpdateBy(SecurityUtils.getUserCode());
            templateCenter.setUpdateTime(DateUtils.getNowDate());
            count += templateCenterMapper.updateByUploadFileId(templateCenter);
        }
        return count;
    }
}
