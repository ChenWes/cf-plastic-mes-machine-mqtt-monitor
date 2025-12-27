package com.cf.framework.manager.factory;

import java.util.TimerTask;

import com.cf.common.utils.spring.SpringUtils;
import com.cf.system.domain.SysOperationLog;
import com.cf.system.service.ISysOperationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cf.common.utils.LogUtils;
import com.cf.common.utils.ServletUtils;
import com.cf.common.utils.ip.AddressUtils;
import com.cf.common.utils.ip.IpUtils;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * 异步工厂（产生任务用）
 * 
 * @author WesChen
 */
public class AsyncFactory
{
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    /**
     * 记录登录信息
     * 
     * @param username 用户名
     * @param status 状态
     * @param message 消息
     * @param args 列表
     * @return 任务task
     */
    public static TimerTask recordLogininfor(final String username, final String status, final String message,
            final Object... args)
    {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        return new TimerTask()
        {
            @Override
            public void run()
            {
                //TODO： save  data

            }
        };
    }

    /**
     * 操作日志记录
     * 
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final SysOperationLog operLog)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
               SpringUtils.getBean(ISysOperationLogService.class).insertSysOperationLog(operLog);
            }
        };
    }
}
