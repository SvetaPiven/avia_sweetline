package com.avia.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Aspect
public class MethodExecutionCountAspect {

    private static final Logger log = Logger.getLogger(MethodExecutionCountAspect.class);
    private static final Map<String, AtomicInteger> count = new ConcurrentHashMap<>();

    @Pointcut("execution(* com.avia.service.impl.*.*(..))")
    public void executionCountAllMethod() {
    }

    @AfterReturning("executionCountAllMethod()")
    public Object logCountExecutionMethods(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        if (count.containsKey(methodName)) {
            count.get(methodName).incrementAndGet();
        } else {
            count.put(methodName, new AtomicInteger(1));
        }
        log.info(" 'Method' " + methodName + " has been called " + count.get(methodName) + " times.");
        return joinPoint;
    }
}
