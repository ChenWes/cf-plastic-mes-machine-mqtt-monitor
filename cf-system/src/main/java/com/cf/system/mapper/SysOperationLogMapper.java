package com.cf.system.mapper;

import com.cf.system.domain.SysOperationLog;

import java.util.List;

/**
 * 系统操作日志Mapper接口
 *
 * @author WesChen
 */
public interface SysOperationLogMapper {
    /**
     * 查询系统操作日志
     *
     * @param logId 系统操作日志ID
     * @return 系统操作日志
     */
    SysOperationLog getSysOperationLogById(Long logId);

    /**
     * 查询系统操作日志列表
     *
     * @param sysOperationLog 系统操作日志
     * @return 系统操作日志集合
     */
    List<SysOperationLog> getSysOperationLogList(SysOperationLog sysOperationLog);

    /**
     * 新增系统操作日志
     *
     * @param sysOperationLog 系统操作日志
     * @return 结果
     */
    int insertSysOperationLog(SysOperationLog sysOperationLog);

    /**
     * 修改系统操作日志
     *
     * @param sysOperationLog 系统操作日志
     * @return 结果
     */
    int updateSysOperationLog(SysOperationLog sysOperationLog);

    /**
     * 删除系统操作日志
     *
     * @param logId 系统操作日志ID
     * @return 结果
     */
    int deleteSysOperationLogById(Long logId);

    /**
     * 批量删除系统操作日志
     *
     * @param logIds 需要删除的数据ID
     * @return 结果
     */
    int deleteSysOperationLogByIds(Long[] logIds);
}
