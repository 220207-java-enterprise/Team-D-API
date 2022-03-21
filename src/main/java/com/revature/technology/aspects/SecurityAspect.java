package com.revature.technology.aspects;


import com.revature.technology.dtos.responses.Principal;
import com.revature.technology.services.TokenService;
import com.revature.technology.services.UserService;
import com.revature.technology.util.exceptions.AuthenticationException;
import com.revature.technology.util.exceptions.ForbiddenException;
import com.revature.technology.util.security.Secured;
import com.revature.technology.util.security.SecurityContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
public class SecurityAspect {

    private TokenService tokenService;
    private SecurityContext securityContext;

    @Autowired
    public SecurityAspect(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @Before("@annotation(com.revature.technology.util.security.Secured)")
    public void secureEndpoint(JoinPoint jp) {
        MethodSignature sig = (MethodSignature) jp.getSignature();
        Method method = sig.getMethod();
        Secured myAnnotation = method.getAnnotation(Secured.class);


        Principal requester = tokenService.extractRequesterDetails(getCurrentRequest().getHeader("Authorization"));
        if (requester != null) {
            for (String myRole : myAnnotation.allowedRoles()) {
                if (Objects.equals(myRole, requester.getRole())) {
                    securityContext.setRequester(requester);
                    return;
                }
            }
            throw new ForbiddenException();
        }
        else throw new AuthenticationException("Please log in before making Requests");
    }

    private HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }
}
