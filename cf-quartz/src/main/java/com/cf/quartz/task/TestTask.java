package com.cf.quartz.task;

import com.cf.common.core.token.ITokenService;
import com.cf.common.utils.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.cf.common.utils.StringUtils;

/**
 * 定时任务调度测试
 * 
 * @author WesChen
 */
@Slf4j
@Component("testTask")
public class TestTask
{

    public void testMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        log.info(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void testParams(String params)
    {
        log.info("执行有参方法：" + params);
    }

    public void testNoParams()
    {
        log.info("执行无参方法");
        //TODO:test
        /*ITokenService tokenService = SpringUtils.getBean(ITokenService.class);
        log.info("result:"+tokenService.getCurrentLangInfo().size());

         */

    }
}
