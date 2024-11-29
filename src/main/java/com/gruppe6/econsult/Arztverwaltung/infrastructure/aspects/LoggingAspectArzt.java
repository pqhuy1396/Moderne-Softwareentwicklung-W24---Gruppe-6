package com.gruppe6.econsult.Arztverwaltung.infrastructure.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspectArzt {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspectArzt.class);

    @Pointcut("within(com.gruppe6.econsult.Arztverwaltung.domain.events.ArztController)")
    public void arztControllerMethods() {}

    @Before("arztControllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering method: {} with arguments: {}", 
                    joinPoint.getSignature().getName(), 
                    joinPoint.getArgs());
    }

    @AfterReturning(value = "arztControllerMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Exiting method: {} with result: {}", 
                    joinPoint.getSignature().getName(), 
                    result);
    }

    @AfterThrowing(value = "arztControllerMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method: {} with cause: {}", 
                     joinPoint.getSignature().getName(), 
                     exception.getMessage());
    }
}
