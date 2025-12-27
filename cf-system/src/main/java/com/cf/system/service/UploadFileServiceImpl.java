package com.cf.system.service;

import com.cf.common.constant.Constants;
import com.cf.common.core.domain.entity.UploadFile;
import com.cf.common.core.minio.IUploadFileService;
import com.cf.common.core.token.ITokenService;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.ListUtil;
import com.cf.common.utils.StringUtils;
import com.cf.system.mapper.UploadFileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description 文件上传记录业务实现
 * @Author ccw
 * @Date 2021/6/1
 */
@Service
@Slf4j
public class UploadFileServiceImpl implements IUploadFileService {
    @Autowired
    private UploadFileMapper uploadFileMapper;

    @Autowired
    private ITokenService tokenService;

    /**
     * 查询记录
     *
     * @param uploadFileId 记录ID
     * @return 文件上传记录
     */
    @Override
    public UploadFile getUploadFileById(String uploadFileId) {
        if (StringUtils.isEmpty(uploadFileId)) {
            return null;
        }
        UploadFile uploadFile = uploadFileMapper.getUploadFileById(uploadFileId);
        return uploadFile;
    }

    @Override
    public List<UploadFile> getUploadFileListByIds(List<String> uploadFileIds) {
        uploadFileIds = ListUtil.getDistinctList(uploadFileIds);
        if (CollectionUtils.isEmpty(uploadFileIds)) {
            return new ArrayList<>();
        }
        return uploadFileMapper.getUploadFileListByIds(uploadFileIds);
    }

    /**
     * 新增文件上传记录
     *
     * @param uploadFile 文件上传记录
     * @return 结果
     */
    @Override
    public int insertUploadFile(UploadFile uploadFile) {
        uploadFile.setCreateBy(tokenService.getUserCode());
        uploadFile.setCreateTime(DateUtils.getNowDate());
        uploadFile.setStatus(Constants.STATUS_YES);
        return uploadFileMapper.insertUploadFile(uploadFile);
    }

    /**
     * 修改文件上传记录
     *
     * @param uploadFile 文件上传记录
     * @return 结果
     */
    @Override
    public int updateUploadFile(UploadFile uploadFile) {
        uploadFile.setUpdateBy(tokenService.getUserCode());
        uploadFile.setUpdateTime(DateUtils.getNowDate());
        return uploadFileMapper.updateUploadFile(uploadFile);
    }

    /**
     * 删除文件上传记录
     *
     * @param uploadFileId ID
     * @return 结果
     */
    @Override
    public int deleteUploadFileById(String uploadFileId) {
        return uploadFileMapper.deleteUploadFileById(uploadFileId);
    }

    @Override
    public Map<String, UploadFile> getUploadFileKeyMap(List<String> uploadFileIds) {
        if (CollectionUtils.isEmpty(uploadFileIds)) {
            return new HashMap<>();
        }
        List<UploadFile> list = this.getUploadFileListByIds(uploadFileIds);
        // to key map
        return list.stream().collect(Collectors.toMap(UploadFile::getUploadFileId, Function.identity()));
    }
}
