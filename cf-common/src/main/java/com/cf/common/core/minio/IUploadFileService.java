package com.cf.common.core.minio;

import com.cf.common.core.domain.entity.UploadFile;

import java.util.List;
import java.util.Map;

/**
 * @Description 文件上传记录业务接口
 * @Author ccw
 * @Date 2021/6/1
 */
public interface IUploadFileService {
    /**
     * 查询记录
     *
     * @param uploadFileId 记录ID
     * @return 文件上传记录
     */
    UploadFile getUploadFileById(String uploadFileId);

    List<UploadFile> getUploadFileListByIds(List<String> uploadFileIds);

    /**
     * 新增文件上传记录
     *
     * @param uploadFile 文件上传记录
     * @return 结果
     */
    int insertUploadFile(UploadFile uploadFile);

    /**
     * 修改文件上传记录
     *
     * @param uploadFile 文件上传记录
     * @return 结果
     */
    int updateUploadFile(UploadFile uploadFile);

    /**
     * 删除文件上传记录
     *
     * @param uploadFileId ID
     * @return 结果
     */
    int deleteUploadFileById(String uploadFileId);

    Map<String, UploadFile> getUploadFileKeyMap(List<String> uploadFileIds);
}
