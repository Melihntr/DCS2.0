package com.melih.spring.E7;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // Tüm UserService metodlarını hedefleyen pointcut
    @Around("execution(* com.example.service.UserService.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // Hedef metodun çağrılması
        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();

        System.out.println("Method: " + methodName + " | Execution Time: " + (endTime - startTime) + " ms");

        return result;
    }
}
