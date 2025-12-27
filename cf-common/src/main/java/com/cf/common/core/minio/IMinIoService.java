package com.cf.common.core.minio;

import com.cf.common.core.domain.entity.UploadFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description MinIo上传下载业务接口
 * @Author ccw
 * @Date 2021/5/28
 */
public interface IMinIoService {
    /**
     * 上传文件
     *
     * @param bucketName 桶名
     * @param file       文件
     * @return
     */
    UploadFile upload(String bucketName, MultipartFile file, String fileName);


    /**
     * 下载文件
     *
     * @param uploadFileId
     * @param response
     */
    void download(String uploadFileId, HttpServletResponse response);


    /**
     * 流式查看文件
     * @param uploadFileId
     * @param response
     */
    ResponseEntity<StreamingResponseBody> streamView(String uploadFileId, @RequestHeader(value = "Range", required = false) String rangeHeader, HttpServletResponse response);


    /**
     * 删除文件
     *
     * @param uploadFileId
     * @return Boolean
     */
    Boolean remove(String uploadFileId);


    /**
     * 复制文件（复制在同一bucket）
     * @param oldPath 旧路径id
     * @param newPath 新路径id
     */
    void copyFile(String oldPath, String newPath);
}
