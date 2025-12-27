package com.cf.framework.aspectj;

import com.cf.common.annotation.MultilayerLang;
import com.cf.common.core.i18n.IDataI18nService;
import com.cf.common.core.token.ITokenService;
import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoKeyFieldName;
import com.cf.common.enums.LangInfoTableName;
import com.cf.common.utils.reflect.ReflectUtils;
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
 * @Description 多次数据多语言处理
 * @Author ccw
 * @Date 26/5/2022
 */
@Aspect
@Component
public class MultilayerLangAspect {

    private static final Logger log = LoggerFactory.getLogger(LangAspect.class);

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private IDataI18nService dataI18nService;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.cf.common.annotation.MultilayerLang)")
    public void multilayerLPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "multilayerLPointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        handleMultilayerLang(joinPoint, result);
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private MultilayerLang getAnnotationMultilayerLang(JoinPoint joinPoint) throws Exception {

        //获取签名
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        //得到方法
        Method method = methodSignature.getMethod();
        if (method != null) {

            //获取方法的注解
            return method.getAnnotation(MultilayerLang.class);
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
    protected void handleMultilayerLang(final JoinPoint joinPoint, Object result) {
        try {
            // 获得注解
            MultilayerLang multilayerLangAttr = getAnnotationMultilayerLang(joinPoint);

            if (multilayerLangAttr == null) {
                multilayerLangAttr = joinPoint.getTarget().getClass().getAnnotation(MultilayerLang.class);
                if (multilayerLangAttr == null) {
                    return;
                }
            }

            //返回的记录实体
            if (result == null) {
                return;
            }

            //获取列名
            LangInfoColumnName[] columnNameArray = multilayerLangAttr.columnNames();
            if (columnNameArray.length == 0) {
                return;
            }

            //获取key列名
            LangInfoKeyFieldName[] keyFieldNameArray = multilayerLangAttr.keyFieldNames();
            if (keyFieldNameArray == null) {
                return;
            }

            //获取表名
            LangInfoTableName[] tableNameArray = multilayerLangAttr.tableNames();
            if (tableNameArray == null) {
                return;
            }

            //获取位置
            String[] pathArray = multilayerLangAttr.paths();
            if (pathArray == null) {
                return;
            }

            //数组长度不一致，返回
            Boolean isSameLength = columnNameArray.length == keyFieldNameArray.length && keyFieldNameArray.length == tableNameArray.length && tableNameArray.length == pathArray.length;
            if (!isSameLength) {
                return;
            }

            getHandle(tableNameArray, columnNameArray, keyFieldNameArray, pathArray, result);
        } catch (Exception exp) {
            log.error("handleLang exception:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    private void getHandle(LangInfoTableName[] tableNameArray, LangInfoColumnName[] columnNameArray, LangInfoKeyFieldName[] keyFieldNameArray, String[] pathArray, Object result) {
        // 获取当前语言类型
        String lang = tokenService.getCurrentLangType().toString();
        // 国际化操作
        for (int i = 0; i < tableNameArray.length; i++) {
            // path 切割
            String[] filedNames = pathArray[i].split("_");

            if (result instanceof java.util.List) {
                //如果传入的对象是列表则进行循环处理
                for (Object obj : ((List<?>) result).toArray()) {
                    if (obj == null) {
                        continue;
                    }
                    this.handleObject(filedNames, obj, 0, tableNameArray[i], columnNameArray[i], keyFieldNameArray[i], lang);
                }
            } else {
                //如果传入的非列表，则是普通对象直接处理即可
                this.handleObject(filedNames, result, 0, tableNameArray[i], columnNameArray[i], keyFieldNameArray[i], lang);
            }
        }
    }

    private void setObjLangInfo(Object obj, LangInfoTableName tableName, LangInfoColumnName columnName, LangInfoKeyFieldName keyFieldName, String lang) {
        dataI18nService.translate(tableName.getValue(), columnName.getValue(), keyFieldName.getValue(), lang, obj);
    }


    /**
     * 处理object
     */
    private void handleObject(String[] filedNames, Object obj, int initIndex, LangInfoTableName tableName, LangInfoColumnName columnName, LangInfoKeyFieldName keyFieldName, String lang) {
        if (obj instanceof java.util.List) {
            for (Object object : ((List<?>) obj).toArray()) {
                if (object == null) {
                    continue;
                }
                //当前对象
                Object currentObject = object;
                for (int i = initIndex; i < filedNames.length; i++) {
                    //最后一位处理多语言
                    if (i == filedNames.length - 1) {
                        //处理数据
                        setObjLangInfo(currentObject, tableName, columnName, keyFieldName, lang);
                        break;
                    }
                    //获取对象
                    currentObject = ReflectUtils.getFieldValue(currentObject, filedNames[i]);
                    if (currentObject == null) {
                        break;
                    }
                    //如果是列表
                    if (currentObject instanceof java.util.List) {
                        //调用自身
                        handleObject(filedNames, currentObject, i + 1, tableName, columnName, keyFieldName, lang);
                        break;
                    }

                }
            }
        } else {
            Object currentObject = obj;
            for (int i = initIndex; i < filedNames.length; i++) {
                if (i == filedNames.length - 1) {
                    //处理数据
                    setObjLangInfo(currentObject, tableName, columnName, keyFieldName, lang);
                    break;
                }
                currentObject = ReflectUtils.getFieldValue(currentObject, filedNames[i]);
                if (currentObject == null) {
                    break;
                }
                //如果是列表
                if (currentObject instanceof java.util.List) {
                    handleObject(filedNames, currentObject, i + 1, tableName, columnName, keyFieldName, lang);
                    break;
                }
            }
        }
    }
}
