package com.my.el.entity;

import ch.qos.logback.classic.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Aspect
@Component
public class LogAspect {
    private Logger logger = (Logger) LoggerFactory.getLogger(getClass().getName());
    private ObjectError error;

    @Pointcut("execution(* com.my.el.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
         HttpServletRequest request = attributes.getRequest();
        logger.info("-------------------用户发起请求-----------------");
        // 记录下请求内容
        //   logger.info("URL : " + request.getRequestURL().toString());
        //   logger.info("HTTP_METHOD : " + request.getMethod());
        //如果是表单，参数值是普通键值对。如果是application/json，则request.getParameter是取不到的。
        //   logger.info("HTTP_HEAD Type : " + request.getHeader("Content-Type"));
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("用户名");
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        if ("application/json".equals(request.getHeader("Content-Type"))) {
            //记录application/json时的传参，SpringMVC中使用@RequestBody接收的值
            logger.info(getRequestPayload(request));
        } else {
            //记录请求的键值对
            for (String key : request.getParameterMap().keySet()) {
                logger.info(key + "----" + request.getParameter(key));
            }
        }

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("方法的返回值 : " + ret);
        logger.info("------------------请求结束------------------");
    }

    //后置异常通知
    //   @AfterThrowing(throwing = "ex", pointcut = "webLog()")
    //   public void throwss(JoinPoint jp, Exception ex){
    //       logger.info("方法异常时执行.....");
    //  }

    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
    //  @After("webLog()")
    //   public void after(JoinPoint jp){
//        logger.info("方法最后执行.....");
    //  }

 private String getRequestPayload(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        try(BufferedReader reader = req.getReader()) {
            char[]buff = new char[1024];
            int len;
            while((len = reader.read(buff)) != -1) {
                sb.append(buff,0, len);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}
//    此处是对所有controller类的切面编程
//
//            切点方程
//在各个pattern中可以使用“*”来表示匹配所有。在(param-pattern)中，可以指定具体的参数类型，多个参数间用“,”隔开，各个也可以用“*”来表示匹配任意类型的参数，如(String)表示匹配一个String参数的方法；(*,String)表示匹配有两个参数的方法，第一个参数可以是任意类型，而第二个参数是String类型；可以用(..)表示零个或多个任意参数。
//        现在来看看几个例子：
//        1）execution(* *(..))
//        表示匹配所有方法
//        2）execution(public * com. savage.service.UserService.*(..))
//        表示匹配com.savage.server.UserService中所有的公有方法
//        3）execution(* com.savage.server..*.*(..))
//        表示匹配com.savage.server包及其子包下的所有方法



