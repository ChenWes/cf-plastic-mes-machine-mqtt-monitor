package com.cf.framework.web.service;

import com.cf.common.config.MinIoConfig;
import com.cf.common.constant.BucketName;
import com.cf.common.constant.MessageCode;
import com.cf.common.core.domain.entity.UploadFile;
import com.cf.common.core.minio.*;
import com.cf.common.exception.CustomException;
import com.cf.common.utils.StringUtils;
import com.cf.common.utils.file.FileUploadUtils;
import com.cf.common.utils.file.MimeTypeUtils;
import com.cf.common.utils.uuid.IdUtils;
import io.minio.*;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.security.MessageDigest;

import static com.cf.common.utils.file.FileUtils.getFileMD5;

/**
 * @Description MinIo上传下载业务实现
 * @Author ccw
 * @Date 2021/5/28
 */
@Service
public class MinIoServiceImpl implements IMinIoService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinIoConfig minIoConfig;

    @Autowired
    private IUploadFileService uploadFileService;

    @Autowired
    private IEmployeeAvatarService employeeAvatarService;

    /**
     * 创建 bucket
     *
     * @param bucketName: 桶名
     * @return: void
     */
    @SneakyThrows(Exception.class)
    public void createBucket(String bucketName) {
        boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!isExist) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        }
    }

    @SneakyThrows(Exception.class)
    public boolean checkBucket(String bucketName) {
        boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        return isExist;
    }

    /**
     * 检查对象
     * 2021-08-28 WesChen
     * 因为MinIO不能直接检查文件是否存在，现在使用的是列出对象列表，并且通过指定前缀的方式缩小查询的范围
     *
     * @param bucketName
     * @param objectName
     * @return
     */
    @SneakyThrows(Exception.class)
    public boolean checkObject(String bucketName, String objectName) {
        boolean isExist = false;
        Iterable<Result<Item>> getObject = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).prefix(objectName).build());
        for (Result<Item> result : getObject) {
            Item item = result.get();

            isExist = true;
        }
        return isExist;
    }

    /**
     * 上传文件
     *
     * @param bucketName 桶名
     * @param file
     * @return
     */
    @Override
    @SneakyThrows(Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public UploadFile upload(String bucketName, MultipartFile file, String fileName) {

        //获取文件后缀
        String extension = FileUploadUtils.getExtension(file);
        //获取文件名
        String originalFilename = file.getOriginalFilename();
        //允许上传的文件类型
        String[] allowedExtension = MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION;
        //判断文件是不是允许上传
        if (!FileUploadUtils.isAllowedExtension(extension, allowedExtension)) {
            throw new CustomException(MessageCode.UPLOAD_FILE_FORMAT_ERROR);
        }
        createBucket(bucketName);

        UploadFile uploadFile = new UploadFile();
        String uploadFileId;
        //文件名不为空
        if (StringUtils.isNotEmpty(fileName)) {
            //特殊（如固定辅助文件）上传
            //桶名+文件名+文件类型确保id和文件一一对应
            uploadFileId = bucketName + "_" + fileName + "." + extension;
            uploadFile.setFileName(fileName + "." + extension);

        } else {
            //正常上传
            //获取文件记录id
            uploadFileId = IdUtils.fastSimpleUuid();
            uploadFile.setFileName(uploadFileId + "." + extension);
        }

        uploadFile.setUploadFileId(uploadFileId);
        uploadFile.setBucketsName(bucketName);
        uploadFile.setOriginalFileName(originalFilename);
        uploadFile.setFileSize(file.getSize());
        uploadFile.setFileType(file.getContentType());
        uploadFile.setRemark(getFileMD5(file));
        int rows;
        if (StringUtils.isNotEmpty(fileName)) {
            //检查是否有统一id文件，有就更新，没有就新增
            if (uploadFileService.getUploadFileById(uploadFileId) != null) {
                //更新
                rows = uploadFileService.updateUploadFile(uploadFile);
            } else {
                //新增
                rows = uploadFileService.insertUploadFile(uploadFile);
            }

        } else {
            //正常上传
            rows = uploadFileService.insertUploadFile(uploadFile);
        }

        if (rows > 0) {
            PutObjectArgs objectArgs = PutObjectArgs.builder().object(uploadFile.getFileName())
                    .bucket(bucketName)
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(), file.getSize(), -1).build();
            minioClient.putObject(objectArgs);
        }
        return uploadFileService.getUploadFileById(uploadFile.getUploadFileId());
    }

    /**
     * 下载文件
     *
     * @param uploadFileId
     * @param response
     */
    @Override
    @SneakyThrows(Exception.class)
    public void download(String uploadFileId, HttpServletResponse response) {
        UploadFile uploadFile = uploadFileService.getUploadFileById(uploadFileId);
        if (uploadFile != null) {
            StatObjectResponse objectStat = minioClient.statObject(StatObjectArgs.builder().bucket(uploadFile.getBucketsName()).object(uploadFile.getFileName()).build());
            response.setContentType(objectStat.contentType());

            //下载时，下载文件名为原文件名
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(uploadFile.getOriginalFileName().getBytes("UTF-8"), "ISO8859-1"));

            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(uploadFile.getBucketsName())
                    .object(uploadFile.getFileName())
                    .build());
            IOUtils.copy(inputStream, response.getOutputStream());
            inputStream.close();
        }
    }

    @Override
    @SneakyThrows(Exception.class)
    public ResponseEntity<StreamingResponseBody> streamView(String uploadFileId, @RequestHeader(value = "Range", required = false) String rangeHeader, HttpServletResponse response) {

        final int BUFFER_SIZE = 8192; // 8KB
        final long DEFAULT_CHUNK_SIZE = 1024 * 1024 * 5; // 5MB

        // 查找文件
        UploadFile uploadFile = uploadFileService.getUploadFileById(uploadFileId);

        // 判断文件是否存在
        if (uploadFile == null) {
            return ResponseEntity.notFound().build();
        }


        try {
            // 找出文件
            StatObjectResponse objectStat = minioClient.statObject(StatObjectArgs.builder().bucket(uploadFile.getBucketsName()).object(uploadFile.getFileName()).build());
            // 获取文件类型
            String contentType = objectStat.contentType();

            // 获取文件大小
            long fileSize = objectStat.size();
            // 解析Range头
            long startByte = 0;
            long endByte = fileSize - 1;


            if (!StringUtils.isEmpty(rangeHeader)) {
                try {
                    String[] ranges = rangeHeader.substring(6).split("-");
                    startByte = Long.parseLong(ranges[0]);


                    if (ranges.length > 1 && !ranges[1].isEmpty()) {
                        endByte = Long.parseLong(ranges[1]);
                    } else {
                        // 关键改进：如果没有指定结束位置，限制最大块大小
                        endByte = Math.min(startByte + DEFAULT_CHUNK_SIZE, fileSize - 1);
                    }

                    // 验证范围是否有效
                    if (startByte >= fileSize) {
                        return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
                                .header(HttpHeaders.CONTENT_RANGE, "bytes */" + fileSize)
                                .build();
                    }

                    // 确保endByte不超过文件大小
                    if (endByte >= fileSize) {
                        endByte = fileSize - 1;
                    }
                } catch (NumberFormatException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }

            } else {
                // 如果没有Range头，返回小块数据或重定向到支持Range的请求
                endByte = Math.min(DEFAULT_CHUNK_SIZE - 1, fileSize - 1);
            }

            // 计算实际读取的长度
            final long contentLength = endByte - startByte + 1;
            final long finalStartByte = startByte;

            StreamingResponseBody responseBody = outputStream -> {

                try (InputStream inputStream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(uploadFile.getBucketsName())
                                .object(uploadFile.getFileName())
                                .offset(finalStartByte)
                                .length(contentLength)
                                .build())) {

                    byte[] buffer = new byte[BUFFER_SIZE];
                    long bytesRemaining = contentLength;
                    int bytesRead;

                    while (bytesRemaining > 0 && (bytesRead = inputStream.read(buffer, 0, (int) Math.min(buffer.length, bytesRemaining))) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                        bytesRemaining -= bytesRead;
                        outputStream.flush();
                    }

                } catch (Exception e) {

                }
            };

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", contentType);
            headers.add("Accept-Ranges", "bytes");
            headers.add("Content-Length", String.valueOf(contentLength));
            // 如果是范围请求，返回206 Partial Content
            headers.add("Content-Range", String.format("bytes %d-%d/%d", startByte, endByte, fileSize));

            return new ResponseEntity<>(responseBody, headers, HttpStatus.PARTIAL_CONTENT);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }

    /**
     * 删除文件
     *
     * @param uploadFileId
     * @return Boolean
     */
    @Override
    @SneakyThrows(Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public Boolean remove(String uploadFileId) {
        UploadFile uploadFile = uploadFileService.getUploadFileById(uploadFileId);
        int rows = uploadFileService.deleteUploadFileById(uploadFileId);
        if (uploadFile != null && rows > 0) {
            switch (uploadFile.getBucketsName()) {
                case BucketName.EMPLOYEE:
                    employeeAvatarService.updateEmployeeByAvatar(uploadFileId);
                    break;
                default:
                    break;
            }
            //检查桶及对象都存在才删除
            if (checkBucket(uploadFile.getBucketsName()) && checkObject(uploadFile.getBucketsName(), uploadFile.getFileName())) {
                //开始删除记录
                minioClient.removeObject(RemoveObjectArgs.builder()
                        .bucket(uploadFile.getBucketsName())
                        .object(uploadFile.getFileName())
                        .build());
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * 复制文件（复制在同一bucket）
     *
     * @param oldPath 旧路径id
     * @param newPath 新路径id
     */
    @Override
    @SneakyThrows(Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public void copyFile(String oldPath, String newPath) {
        UploadFile uploadFile = uploadFileService.getUploadFileById(oldPath);
        if (uploadFile != null) {
            uploadFile.setUploadFileId(newPath);
            String oldFileName = uploadFile.getFileName();
            String newFileName = oldFileName.replaceFirst(oldPath, newPath);
            uploadFile.setFileName(newFileName);
            //记录保存数据库
            int rows = uploadFileService.insertUploadFile(uploadFile);
            if (rows > 0) {
                if (checkBucket(uploadFile.getBucketsName()) && checkObject(uploadFile.getBucketsName(), oldFileName)) {
                    minioClient.copyObject(CopyObjectArgs.builder()
                            .bucket(uploadFile.getBucketsName())
                            .object(newFileName)
                            .source(CopySource.builder()
                                    .bucket(uploadFile.getBucketsName())
                                    .object(oldFileName)
                                    .build())
                            .build());
                }
            }
        }
    }
}
