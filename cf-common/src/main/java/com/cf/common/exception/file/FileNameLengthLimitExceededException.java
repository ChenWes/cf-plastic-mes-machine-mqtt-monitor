package com.cf.common.exception.file;

import com.cf.common.constant.MessageCode;

/**
 * 文件名称超长限制异常类
 * 
 * @author WesChen
 */
public class FileNameLengthLimitExceededException extends FileException
{
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength)
    {
        super(MessageCode.UPLOAD_FILENAME_EXCEED_LENGTH, new Object[] { defaultFileNameLength });
    }
}
