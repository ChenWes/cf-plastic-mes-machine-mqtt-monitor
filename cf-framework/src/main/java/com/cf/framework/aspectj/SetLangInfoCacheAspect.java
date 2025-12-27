package com.cf.framework.aspectj;

import com.cf.common.core.domain.entity.SysLangInfo;
import com.cf.common.core.token.ITokenService;
import com.cf.common.enums.LangInfoTableName;
import com.cf.common.utils.LangInfoUtils;
import com.cf.system.service.ISysLangInfoService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 多语言缓存处理
 * @Author ccw
 * @Date 2021/5/10
 */
@Aspect
@Component
public class SetLangInfoCacheAspect {

    @Autowired
    private ITokenService tokenService;
    @Autowired
    private ISysLangInfoService sysLangInfoService;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.cf.common.annotation.SetLangInfoCache)")
    public void setLangInfoCachePointCut() {
    }

    /**
     * 处理完请求后执行
     */
    @AfterReturning(pointcut = "setLangInfoCachePointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {

        Object firstArg = getFirstArg(joinPoint);
        String tableName = "";

        //通过第一个参数类型判断
        if (firstArg instanceof LangInfoTableName) {

            //普通类型，updateLangInfoByTableNameAndColumnNameAndRecordId/deleteLangInfoByRecordIdsAndTableName方法
            tableName = ((LangInfoTableName) firstArg).getValue();

        } else if (firstArg instanceof SysLangInfo) {

            //插入或修改单个实体，insertLangInfo/updateLangInfo方法
            tableName = ((SysLangInfo) firstArg).getTableName();

        }


        //获取当前数据
        String currentLangInfo = tokenService.getCurrentLangType().toString();

        //通过语言获取
        List<SysLangInfo> list = LangInfoUtils.getLangInfoCache(tableName, currentLangInfo);

        if (list != null) {

//            list = sysLangInfoService.getLangInfoByLang(currentLangInfo);

            //从数据库中获取多语言记录
            list = sysLangInfoService.getLangInfoByLangAndTableName(currentLangInfo, tableName);

            //将记录设置至缓存中
            LangInfoUtils.setLangInfoCache(tableName, currentLangInfo, list);
        }
    }

    private Object getFirstArg(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            return args[0];
        }
        return null;
    }
}
