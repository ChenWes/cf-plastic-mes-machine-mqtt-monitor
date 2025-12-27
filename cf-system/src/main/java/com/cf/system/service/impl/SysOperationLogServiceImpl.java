package com.cf.system.service.impl;

import com.cf.system.domain.SysOperationLog;
import com.cf.system.mapper.SysOperationLogMapper;
import com.cf.system.service.ISysOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统操作日志Service业务层处理
 * 
 * @author WesChen
 */
@Service
public class SysOperationLogServiceImpl implements ISysOperationLogService 
{
    @Autowired
    private SysOperationLogMapper sysOperationLogMapper;

    /**
     * 查询系统操作日志
     * 
     * @param logId 系统操作日志ID
     * @return 系统操作日志
     */
    @Override
    public SysOperationLog getSysOperationLogById(Long logId)
    {
        return sysOperationLogMapper.getSysOperationLogById(logId);
    }

    /**
     * 查询系统操作日志列表
     * 
     * @param sysOperationLog 系统操作日志
     * @return 系统操作日志
     */
    @Override
    public List<SysOperationLog> getSysOperationLogList(SysOperationLog sysOperationLog)
    {
        return sysOperationLogMapper.getSysOperationLogList(sysOperationLog);
    }

    /**
     * 新增系统操作日志
     * 
     * @param sysOperationLog 系统操作日志
     * @return 结果
     */
    @Override
    public int insertSysOperationLog(SysOperationLog sysOperationLog)
    {
        return sysOperationLogMapper.insertSysOperationLog(sysOperationLog);
    }

    /**
     * 修改系统操作日志
     * 
     * @param sysOperationLog 系统操作日志
     * @return 结果
     */
    @Override
    public int updateSysOperationLog(SysOperationLog sysOperationLog)
    {
        return sysOperationLogMapper.updateSysOperationLog(sysOperationLog);
    }

    /**
     * 批量删除系统操作日志
     * 
     * @param logIds 需要删除的系统操作日志ID
     * @return 结果
     */
    @Override
    public int deleteSysOperationLogByIds(Long[] logIds)
    {
        return sysOperationLogMapper.deleteSysOperationLogByIds(logIds);
    }

    /**
     * 删除系统操作日志信息
     * 
     * @param logId 系统操作日志ID
     * @return 结果
     */
    @Override
    public int deleteSysOperationLogById(Long logId)
    {
        return sysOperationLogMapper.deleteSysOperationLogById(logId);
    }
}
