package com.gruppe6.econsult.Patientenverwaltung.infrastructure.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspectPatient {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspectPatient.class);

    @Pointcut("within(com.gruppe6.econsult.Patientenverwaltung.domain.events.PatientController)")
    public void patientControllerMethods() {}

    @Before("patientControllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering method: {} with arguments: {}", 
                    joinPoint.getSignature().getName(), 
                    joinPoint.getArgs());
    }

    @AfterReturning(value = "patientControllerMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Exiting method: {} with result: {}", 
                    joinPoint.getSignature().getName(), 
                    result);
    }

    @AfterThrowing(value = "patientControllerMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method: {} with cause: {}", 
                     joinPoint.getSignature().getName(), 
                     exception.getMessage());
    }
}
