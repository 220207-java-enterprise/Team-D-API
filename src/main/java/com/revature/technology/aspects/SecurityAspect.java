package com.revature.technology.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {


    @Before("within(com.revature.technology.controllers..*)")
    public void secureEndpoint(JoinPoint jp){

    }

}
