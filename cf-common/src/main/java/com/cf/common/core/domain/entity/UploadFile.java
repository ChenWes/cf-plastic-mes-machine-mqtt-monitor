package com.cf.common.core.domain.entity;

import com.cf.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 文件上传记录实体
 * @Author ccw
 * @Date 2021/6/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UploadFile extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 文件上传id
     */
    private String uploadFileId;

    /**
     * 桶名
     */
    private String bucketsName;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 上传原文件名
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
     * 部门状态:Y正常,N停用
     */
    private String status;
}
