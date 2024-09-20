package com.productapi.aop;

import com.productapi.dto.ProductDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class ProductAspect {

    Logger logger = LoggerFactory.getLogger("ProductAspect.class");

    @Before("execution(* com.productapi.controller.HomeController.*(..))")
    public void beforeAdviceForSecurityConfig(JoinPoint joinPoint){
        logger.debug("@AOP:: Request to " + joinPoint.getSignature() + " started at " + new Date() );
    }

    @After("execution(* com.productapi.controller.HomeController.*(..))")
    public void aftereAdvice(JoinPoint joinPoint){
        logger.debug("@AOP:: Request to " + joinPoint.getSignature() + " ended at " + new Date() );
    }

//    @AfterReturning("execution(* com.productapi.controller.HomeController.*(..))")
//    public void afterReturningAdviceForService(JoinPoint joinPoint, ProductDto productDto){
//        logger.debug("@AOP:: Request to service layer: Create Employee" + productDto  );
//    }

    @AfterThrowing(value= "execution(* com.productapi.controller.HomeController.*(..))" , throwing = "exception")
    public void afterThrowingAdviceForService(JoinPoint joinPoint, Exception exception){
        logger.debug("@AOP:: Request to service layer: Create Employee: Exception occurred: " + exception.getMessage()  );
    }

//    @Around(value= "execution(* com.dailycodebuffer.employee.services.EmployeeServiceImpl.createEmployee(..))")
//    public ProductDto aroundAdviceForService(ProceedingJoinPoint proceedingJoinPoint){
//        logger.debug("Inside aroundAdviceForService : Create Employee: started at: " + new Date()  );
//        try{
//            return (ProductDto) proceedingJoinPoint.proceed();
//        } catch (Throwable e) {
//            logger.debug("Inside aroundAdviceForService : Create Employee: failed at : " + new Date()  );
//            //throw new RuntimeException(e);
//        }
//        logger.debug("Inside aroundAdviceForService : Create Employee: ended at: " + new Date()  );
//        return null;
//    }
}
