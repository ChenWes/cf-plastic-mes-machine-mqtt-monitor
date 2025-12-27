package com.cf.system.controller.convert;

/**
 * @author coder-ren
 * @date 2024/5/17 14:51
 */

import com.cf.system.controller.response.TemplateCenterVo;
import com.cf.system.domain.TemplateCenter;

/**
 * 启用映射转换
 */
public class TemplateCenterConvert {

    public static TemplateCenterVo toTemplateCenterVo(TemplateCenter templateCenter) {
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

}
