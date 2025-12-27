package com.cf.system.domain;

import com.cf.common.annotation.Excel;
import com.cf.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 模版中心对象 template_center
 *
 * @author wilfmao
 * @date 2024-10-24
 */
public class TemplateCenter extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long templateId;

    /**
     * 平台编号
     */
    @Excel(name = "平台编号")
    private String platformCode;

    /**
     * 系统编号
     */
    @Excel(name = "系统编号")
    private String systemCode;

    /**
     * 模块编号
     */
    @Excel(name = "模块编号")
    private String moduleCode;

    /**
     * 模版类型
     */
    @Excel(name = "模版类型")
    private String templateTypeCode;

    /**
     * 模版编号
     */
    @Excel(name = "模版编号")
    private String templateName;

    /**
     * 上传文件ID
     */
    @Excel(name = "上传文件ID")
    private String uploadFileId;

    /**
     * 原始文件名称
     */
    @Excel(name = "原始文件名称")
    private String originalFileName;

    /**
     * 文件大小
     */
    @Excel(name = "文件大小")
    private Long fileSize;

    /**
     * 文件类型
     */
    @Excel(name = "文件类型")
    private String fileType;

    /**
     * 语言标识
     */
    @Excel(name = "语言标识")
    private String lang;

    /**
     * 标签
     */
    @Excel(name = "标签")
    private String tag;

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setTemplateTypeCode(String templateTypeCode) {
        this.templateTypeCode = templateTypeCode;
    }

    public String getTemplateTypeCode() {
        return templateTypeCode;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setUploadFileId(String uploadFileId) {
        this.uploadFileId = uploadFileId;
    }

    public String getUploadFileId() {
        return uploadFileId;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("templateId", getTemplateId())
                .append("platformCode", getPlatformCode())
                .append("systemCode", getSystemCode())
                .append("moduleCode", getModuleCode())
                .append("templateTypeCode", getTemplateTypeCode())
                .append("templateName", getTemplateName())
                .append("uploadFileId", getUploadFileId())
                .append("originalFileName", getOriginalFileName())
                .append("fileSize", getFileSize())
                .append("fileType", getFileType())
                .append("lang", getLang())
                .append("tag", getTag())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
