package com.cf.framework.aspectj;

import com.cf.common.annotation.DeleteLangInfoCache;
import com.cf.common.core.i18n.IDataI18nService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class DeleteLangInfoCacheAspect {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final String FIELD_RECORD_ID = "record_id";

    private static final String FIELD_LANG_INFO_ID = "lang_info_id";

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

    @Pointcut("@annotation(com.cf.common.annotation.DeleteLangInfoCache)"
            + "|| @within(com.cf.common.annotation.DeleteLangInfoCache)")
    public void deleteLangInfoCachePointCut() {
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

    @AfterReturning(pointcut = "deleteLangInfoCachePointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        if (!successed(result)) {
            return;
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        DeleteLangInfoCache annotation = AnnotationUtils.findAnnotation(signature.getMethod(), DeleteLangInfoCache.class);
        if (annotation == null) {
            return;
        }
        // value 对应的 sys_lang_info 中的字段
        String field = annotation.field();
        // 判断删除操作的字段
        if (FIELD_RECORD_ID.equals(field)) {
            deleteCacheByComboKeys(annotation, joinPoint);
        } else if (FIELD_LANG_INFO_ID.equals(field)) {
            // TODO: 使用主键进行删除, 后期有需要再做扩展, 目前暂时没有根据主键删除的场景。
            // 1. 切面调整为环绕切面
            // 2. 在进行删除操作前，先根据主键ID查询到对应的 tableName , columnName , recordId
            // 3. 然后在环绕通知最后,判断记录删除成功后执行删除redis 操作
        }
    }

    /**
     * 使用tableName 、 columnName 、 record_id 组合键进行删除
     *
     * @param annotation
     * @param joinPoint
     */
    private void deleteCacheByComboKeys(DeleteLangInfoCache annotation, JoinPoint joinPoint) {
        // 使用tableName 、 [columnName] 、 record_id 组合进行删除
        String tableName = (String) parseExpression(annotation.tableName(), joinPoint);
        /// String columnName = (String) parseExpression(deleteLangInfoCache.columnName(), joinPoint);
        Long[] recordIds;
        // 判断是否批量操作
        if (annotation.multi()) {
            recordIds = (Long[]) parseExpression(annotation.value(), joinPoint);
        } else {
            Long value = (Long) parseExpression(annotation.value(), joinPoint);
            recordIds = new Long[]{value};
        }
        // 删除缓存
        List<String> keys = dataI18nService.generateKeys(null, tableName, null);
        String[] fields = Arrays.stream(recordIds).map(String::valueOf).toArray(String[]::new);
        for (String key : keys) {
            dataI18nService.deleteCache(key, fields);
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
            return "";
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
