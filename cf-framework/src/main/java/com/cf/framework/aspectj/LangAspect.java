package com.cf.framework.aspectj;

import com.cf.common.annotation.Lang;
import com.cf.common.core.i18n.IDataI18nService;
import com.cf.common.core.lang.ICommonSysLangInfoService;
import com.cf.common.core.token.ITokenService;
import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoKeyFieldName;
import com.cf.common.enums.LangInfoTableName;
import com.cf.common.utils.StringUtils;
import com.cf.common.utils.reflect.ReflectUtils;
import com.cf.framework.i18n.I18nIsolateContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 多语言处理
 *
 * @author WesChen
 */
@Aspect
@Component
public class LangAspect {

    private static final String GET = "get";
    private static final String INSERT = "insert";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String ID = "Id";

    private static final Logger log = LoggerFactory.getLogger(LangAspect.class);

    @Autowired
    private ICommonSysLangInfoService commonSysLangInfoService;

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private IDataI18nService dataI18nService;

    /**
     * 配置织入点
     */
    @Pointcut("@within(com.cf.common.annotation.Lang)||@annotation(com.cf.common.annotation.Lang)")
    public void langPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "langPointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        // 阻断数据国际化行为
        Boolean isolate = I18nIsolateContextHolder.getI18nIsolate();
        if (Boolean.TRUE.equals(isolate)) {
            return;
        }
        handleLang(joinPoint, result);
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Lang getAnnotationLang(JoinPoint joinPoint) throws Exception {

        //获取签名
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        //得到方法
        Method method = methodSignature.getMethod();
        if (method != null) {

            //获取方法的注解
            return method.getAnnotation(Lang.class);
        }
        return null;
    }

    /**
     * 获取切面第一个参数
     */
    private Object getFirstArg(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            return args[0];
        }
        return null;
    }

    /**
     * 在方法运行完成后，处理多语言
     * 该方法增加一个切面，在AOP方法中进行处理
     *
     * @param joinPoint 切面点
     * @param result    返回的参数实体
     */
    protected void handleLang(final JoinPoint joinPoint, Object result) {
        try {

            // 获得注解
            Lang langAttr = getAnnotationLang(joinPoint);

            if (langAttr == null) {
                langAttr = joinPoint.getTarget().getClass().getAnnotation(Lang.class);
                if (langAttr == null) {
                    return;
                }
            }

            //返回的记录实体
            if (result == null) {
                return;
            }

            //获取列名
            LangInfoColumnName[] columnNameArray = langAttr.columnNames();
            if (columnNameArray.length == 0) {
                return;
            }

            //获取key列名
            LangInfoKeyFieldName keyFieldName = langAttr.keyFieldName();
            if (keyFieldName == null) {
                return;
            }

            //获取表名
            LangInfoTableName tableName = langAttr.tableName();
            if (tableName == null) {
                return;
            }

            //获取第一个参数
            Object firstParameter = getFirstArg(joinPoint);

            //获取切点方法名
            String methodName = joinPoint.getSignature().getName();

            StringBuffer str = new StringBuffer();
            for (int i = 0; i < methodName.length(); i++) {
                char c = methodName.charAt(i);
                if (Character.isUpperCase(c)) {
                    break;
                } else {
                    str.append(c);
                }
            }

            switch (str.toString()) {
                case DELETE:
                    deleteHandle(firstParameter, tableName, result);
                    break;
                case GET:
                    getHandle(tableName, columnNameArray, keyFieldName, result);
                    break;
                case UPDATE:
                case INSERT:
                    updateHandle(firstParameter, tableName, columnNameArray, keyFieldName, result);
                    break;
                default:
            }
        } catch (Exception exp) {
            log.error("handleLang exception:{}", exp.getMessage());
            exp.printStackTrace();
        }


    }

    /**
     * 处理查询
     *
     * @param tableName
     * @param columnNameArray
     * @param result
     */
    private void getHandle(LangInfoTableName tableName, LangInfoColumnName[] columnNameArray, LangInfoKeyFieldName keyFieldName, Object result) {
        // 获取当前语言类型
        String lang = tokenService.getCurrentLangType().toString();
        if (result instanceof java.util.List) {
            //如果传入的对象是列表则进行循环处理
            for (Object obj : ((List<?>) result).toArray()) {
                if (obj == null) {
                    continue;
                }
                translate(tableName, columnNameArray, keyFieldName, lang, obj);
            }
        } else {
            //如果传入的非列表，则是普通对象直接处理即可
            translate(tableName, columnNameArray, keyFieldName, lang, result);
        }
    }

    /**
     * 数据国际化
     *
     * @param tableName
     * @param columnNameArray
     * @param keyFieldName
     * @param lang
     * @param obj
     */
    private void translate(LangInfoTableName tableName, LangInfoColumnName[] columnNameArray,
                           LangInfoKeyFieldName keyFieldName, String lang, Object obj) {
        for (LangInfoColumnName columnName : columnNameArray) {
            dataI18nService.translate(tableName.getValue(), columnName.getValue(), keyFieldName.getValue(), lang, obj);
        }
    }

    /**
     * 处理插入方法
     *
     * @param firstParameter
     * @param tableName
     * @param columnNameArray
     * @param keyFieldName
     * @param result
     */
    private void updateHandle(Object firstParameter, LangInfoTableName tableName, LangInfoColumnName[] columnNameArray,
                              LangInfoKeyFieldName keyFieldName, Object result) {
        if (result instanceof Integer && Integer.parseInt(result.toString()) > 0) {
            // 判断第一个参数是否为空
            if (firstParameter == null) {
                return;
            }
            // 判断id是否合法
            Long id = ReflectUtils.getFieldValue(firstParameter, keyFieldName.getValue());
            if (id == null || id <= 0) {
                return;
            }
            // 同步更新国际化数据
            for (LangInfoColumnName columnName : columnNameArray) {
                String value = ReflectUtils.getFieldValue(firstParameter, StringUtils.toCamelCase(columnName.getValue()));
                if (StringUtils.isNotEmpty(value)) {
                    //修改多语言记录
                    commonSysLangInfoService.updateLangInfoByTableNameAndColumnNameAndRecordId(tableName.getValue(), columnName.getValue(), id, value);
                }
            }

        }
    }

    /**
     * 处理删除方法
     *
     * @param firstParameter
     * @param tableName
     * @param result
     */
    private void deleteHandle(Object firstParameter, LangInfoTableName tableName, Object result) {
        // 切面返回执行成功执行
        if (result instanceof Integer && Integer.parseInt(result.toString()) > 0) {
            //删除多语言记录
            commonSysLangInfoService.deleteLangInfoByRecordIdsAndTableName(tableName.getValue(), (Long[]) firstParameter);
        }
    }


}
