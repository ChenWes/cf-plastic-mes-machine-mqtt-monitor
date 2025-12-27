package com.cf.framework.aspectj;

import com.cf.common.annotation.PutLangInfoCache;
import com.cf.common.core.domain.entity.SysLangInfo;
import com.cf.common.core.i18n.IDataI18nService;
import com.cf.common.core.token.ITokenService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Aspect
@Component
public class PutLangInfoCacheAspect {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 解析spring el表达式
     */
    private static SpelExpressionParser parser = new SpelExpressionParser();
    /**
     * 获取方法参数定义名称
     */
    private static DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Resource
    private IDataI18nService dataI18nService;

    @Autowired
    private ITokenService tokenService;

    @Pointcut("@annotation(com.cf.common.annotation.PutLangInfoCache)"
            + "|| @within(com.cf.common.annotation.PutLangInfoCache)")
    public void putLangInfoCachePointCut() {
    }

    /**
     * 判断切点方法执行是否成功
     *
     * @param result
     * @return
     */
    private boolean successed(Object result) {
        return result instanceof Integer && Integer.parseInt(result.toString()) >= 0;
    }

    @AfterReturning(pointcut = "putLangInfoCachePointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        if (!successed(result)) {
            return;
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        PutLangInfoCache annotation = AnnotationUtils.findAnnotation(signature.getMethod(), PutLangInfoCache.class);
        if (annotation == null) {
            return;
        }
        if (annotation.multi()) {
            // 批量操作。皮操作中语言已经被设置好。
            List<SysLangInfo> langInfos = (List<SysLangInfo>) parseExpression(annotation.value(), joinPoint);
            cache(langInfos);
        } else {
            // 但对象操作
            if (StringUtils.isEmpty(annotation.tableName()) || StringUtils.isEmpty(annotation.columnName())
                    || StringUtils.isEmpty(annotation.recordId())) {
                return;
            }
            String lang = tokenService.getCurrentLangType().toString();
            // 设置缓存
            String tableName = (String) parseExpression(annotation.tableName(), joinPoint);
            String columnName = (String) parseExpression(annotation.columnName(), joinPoint);
            Long recordId = (Long) parseExpression(annotation.recordId(), joinPoint);
            String value = (String) parseExpression(annotation.value(), joinPoint);
            // 执行cache操作
            SysLangInfo info = new SysLangInfo();
            info.setTableName(tableName);
            info.setColumnName(columnName);
            info.setRecordId(recordId);
            info.setValue(value);
            info.setLang(lang);
            cache(Lists.newArrayList(info));
        }
    }

    private void cache(List<SysLangInfo> infos) {
        if (infos == null || infos.size() == 0) {
            return;
        }
        for (SysLangInfo info : infos) {
            if (StringUtils.isEmpty(info.getTableName()) || StringUtils.isEmpty(info.getColumnName())
                    || info.getRecordId() == null || info.getRecordId() == 0) {
                continue;
            }
            dataI18nService.cache(info);
        }
    }

    /**
     * spring el 解析
     *
     * @param spel
     * @param point
     * @return
     */
    private Object parseExpression(String spel, JoinPoint point) {
        if (StringUtils.isEmpty(spel)) {
            return null;
        }
        MethodSignature signature = (MethodSignature) point.getSignature();
        String[] parameterNames = nameDiscoverer.getParameterNames(signature.getMethod());
        Expression expression = parser.parseExpression(spel);
        EvaluationContext context = new StandardEvaluationContext();
        Object[] args = point.getArgs();
        for (int i = 0; i < args.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        return expression.getValue(context);
    }
}
