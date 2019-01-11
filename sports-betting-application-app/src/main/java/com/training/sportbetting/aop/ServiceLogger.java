package com.training.sportbetting.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Aspect
public class ServiceLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLogger.class);

    @Around("execution(* com.training.sportbetting.service.*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        var methodName = joinPoint.getSignature().getName();
        LOGGER.info("[{}] called.", methodName);

        String args = Arrays.stream(joinPoint.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        LOGGER.info("[{}] Params: {}.", methodName, args);

        var start = System.nanoTime();
        Object returnValue = joinPoint.proceed();
        LOGGER.info("[{}] Return value - {}.", methodName, returnValue);
        var end = System.nanoTime();
        LOGGER.info("[{}] Execution time - {} ms.", methodName, (end - start) / 1_000_000);

        return returnValue;
    }
}
