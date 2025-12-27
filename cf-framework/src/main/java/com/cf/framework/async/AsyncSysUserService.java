package com.cf.framework.async;

import com.cf.common.core.domain.entity.SysUser;
import com.cf.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author coder-ren
 * @date 2024/7/17 11:21
 */
@Slf4j
@Component
public class AsyncSysUserService {

    @Autowired
    private ISysUserService userService;

    /**
     * 异步同步用户信息，此方法为异步方法，不影响正常用户的认证操作
     * userCode是唯一键，使用数据表唯一索引保证数据唯一。
     * 并发创建时，第2+条数据插入会提示，记录重复。此错误为正常错误。
     * Caused by: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry 'xxx' for key 'unique_user_code'
     * @param userCode 用户编号
     * @param userName 用户名称
     * @param email    电子邮件
     */
    @Async("threadPoolTaskExecutor")
    public void asyncSysUser(String userCode, String userName, String email) {
        log.info("AsyncSysUserService.asyncSysUser==> async user ! userCode : {}", userCode);
        try {
            log.info("AsyncSysUserService.asyncSysUser==> async user ! userCode : {}", userCode);
            // 判断用户是否存在
            SysUser existUser = userService.getUserByCode(userCode);
            if (existUser != null) {
                log.info("AsyncSysUserService.asyncSysUser==> user is already exist!! userCode:{}", userCode);
                return;
            }
            //add new user in mes
            SysUser user = new SysUser();
            user.setUserCode(userCode);
            user.setEmail(email);
            user.setUserName(userName);
            // 新增用户信息
            userService.insertUser(user);
        } catch (Exception e) {
            log.error("AsyncSysUserService.asyncSysUser==> async user : {}  error!", userCode, e);
        }
    }
}
