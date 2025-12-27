package com.cf.system.domain;

import com.cf.common.annotation.Excel;
import com.cf.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统操作日志对象 sys_operation_log
 * 
 * @author WesChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOperationLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日志ID */
    private Long logId;

    /** 日志类型 */
    @Excel(name = "日志类型")
    private String logType;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 操作的url */
    @Excel(name = "操作的url")
    private String url;

    /** 调用的方法 */
    @Excel(name = "调用的方法")
    private String method;

    /** 操作参数 */
    @Excel(name = "操作参数")
    private String operParam;

    /** 操作结果 */
    @Excel(name = "操作结果")
    private String operResult;

    /** 操作人 */
    @Excel(name = "操作人")
    private String operUser;

    /** 操作IP */
    @Excel(name = "操作IP")
    private String operIp;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date operTime;

    /** 错误信息 */
    @Excel(name = "错误信息")
    private String errorMsg;

    @Excel(name = "请求Method")
    private String requestMethod;
}
