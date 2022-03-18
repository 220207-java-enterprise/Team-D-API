package com.revature.technology.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);


    @Before("within(com.revature.technology.controllers..*)")
    public void logMethodStart(JoinPoint jp){
        String methodSig = extractMethodSignature(jp);
        String methodArgs = Arrays.toString(jp.getArgs());
        logger.info("{} invoked at {} with the provided arguments: {}", methodSig, LocalDateTime.now(), methodArgs);
    }

    @AfterReturning(value = "within(com.revature.technology.controllers..*)", returning = "returnedObj")
    public void logMethodReturn(JoinPoint jp, Object returnedObj){
        String methodSig = extractMethodSignature(jp);
        logger.info("{} successfully returned at {} with value: {}", methodSig, LocalDateTime.now(), returnedObj);
    }

    @AfterThrowing(value = "within(com.revature.technology.controllers..*)", throwing = "t")
    public void logMethodException(JoinPoint jp, Throwable t){
        String methodSig = extractMethodSignature(jp);
        String exceptionName = t.getClass().getName();
        logger.warn("{} was thrown in the method {} at {} with message {}", exceptionName, methodSig, LocalDateTime.now(), t.getMessage());
    }

    private String extractMethodSignature(JoinPoint jp){
//        System.out.println("Target Object = " + jp.getTarget().getClass());
//        System.out.println("Method Signature = " +jp.getSignature().getName());
        return jp.getTarget().getClass().toString() + "#" + jp.getSignature().getName();
    }

}
