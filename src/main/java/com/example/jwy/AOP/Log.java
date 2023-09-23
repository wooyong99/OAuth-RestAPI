package com.example.jwy.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class Log {
    @Pointcut("execution(* com.example.jwy.Controller..*.*(..))")
    public void cut(){}

    @Before("cut()")
    public void before(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println(String.format("==== %s %s 메소드 Start ====",method.getDeclaringClass().getSimpleName(), method.getName()));
        Object[] args = joinPoint.getArgs();
        for(Object arg : args){
            System.out.println(String.format("type : %s   /  value : %s", arg.getClass().getSimpleName(), arg));
        }
    }

    @AfterReturning("cut()")
    public void after(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println(String.format("==== %s %s 메소드 End ====",method.getDeclaringClass().getSimpleName(), method.getName()));
    }
}
