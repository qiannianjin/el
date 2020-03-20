package com.my.el.entity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TransactionDemo {


    //切点，监听某个逻辑层（横向切面）
    @Pointcut(value="execution(* com.my.el.*.*.*(..))")
    public void point(){
        System.out.println("随便");
    }

    //前置通知
    @Before(value="point()")
    public void before(){
        System.out.println("transaction begin");
    }
    //后置通知
    @AfterReturning(value = "point()")
    public void after(){

        System.out.println("transaction commit");

    }

    //环绕通知
    @Around("point()")
    public void around(ProceedingJoinPoint joinPoint){
        System.out.println("transaction begin");

        System.out.println("transaction commit");
    }



}
