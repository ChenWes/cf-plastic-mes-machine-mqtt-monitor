package com.cf.common.exception.file;

import com.cf.common.constant.MessageCode;

/**
 * 文件名大小限制异常类
 * 
 * @author WesChen
 */
public class FileSizeLimitExceededException extends FileException
{
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize)
    {
        super(MessageCode.UPLOAD_EXCEED_MAXSIZE, new Object[] { defaultMaxSize });
    }
}
