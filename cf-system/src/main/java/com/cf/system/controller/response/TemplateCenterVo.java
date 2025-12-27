package com.cf.system.controller.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 模版中心对象 template_center
 *
 * @author wilfmao
 * @date 2024-10-24
 */
@Data
public class TemplateCenterVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long templateId;

    /**
     * 平台编号
     */
    private String platformCode;

    /**
     * 系统编号
     */
    private String systemCode;

    /**
     * 模块编号
     */
    private String moduleCode;

    /**
     * 模版类型
     */
    private String templateTypeCode;

    private SysDicVo templateTypeEntity;

    /**
     * 模版编号
     */
    private String templateName;

    /**
     * 上传文件ID
     */
    private String uploadFileId;

    /**
     * 原始文件名称
     */
    private String originalFileName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 语言标识
     */
    private String lang;

    /**
     * 标签
     */
    private String tag;

    /**
     * 备注
     */
    private String remark;

}
