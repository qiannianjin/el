package com.my.el.entity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TransactionDemo {

    @Pointcut(value="execution(* com.my.el.*.*.*(..))")
    public void point(){
        System.out.println("随便");
    }


    @Before(value="point()")
    public void before(){
        System.out.println("transaction begin");
    }

    @AfterReturning(value = "point()")
    public void after(){
        System.out.println("transaction commit");
    }

    @Around("point()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("transaction begin");
        joinPoint.proceed();
        System.out.println("transaction commit");
    }
}
