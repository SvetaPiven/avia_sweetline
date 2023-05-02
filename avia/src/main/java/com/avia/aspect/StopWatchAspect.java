package com.avia.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class StopWatchAspect {
    private static final Logger log = Logger.getLogger(StopWatchAspect.class);

    @Pointcut("execution(* com.avia.repository.*.*(..))")
    public void aroundRepositoryPointcut() {
    }

    @Around("aroundRepositoryPointcut()")
    public Object logAroundMethodsAndCheckTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch watch = new StopWatch(joinPoint.toString());
        try {
            watch.start();
            log.info("Method " + joinPoint.getSignature().getName() + " start");
            return joinPoint.proceed();
        } finally {
            watch.stop();
            log.info("Method: " + joinPoint.getSignature().getName() + " working time " + watch.getTotalTimeMillis());
            log.info("Method " + joinPoint.getSignature().getName() + " finished");
        }
    }
}