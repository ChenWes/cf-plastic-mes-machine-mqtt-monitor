package com.cf.framework.aspectj;

import com.cf.common.annotation.I18nIsolate;
import com.cf.framework.i18n.I18nIsolateContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Order(1)
@Component
public class I18nIsolateAspect {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.cf.common.annotation.I18nIsolate)"
            + "|| @within(com.cf.common.annotation.I18nIsolate)")
    public void i18nIsolatePointCut() {

    }

    @Around("i18nIsolatePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        I18nIsolate i18nIsolate = AnnotationUtils.findAnnotation(signature.getMethod(), I18nIsolate.class);
        if (Objects.nonNull(i18nIsolate)) {
            I18nIsolateContextHolder.setI18nIsolate(i18nIsolate.isolate());
        }
        try {
            return point.proceed();
        } finally {
            I18nIsolateContextHolder.clearI18nIsolate();
        }
    }
}
