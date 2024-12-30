package org.example.ch6_1.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
public class LoggingAspect {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Around("execution(* org.example.ch6_1.services.*.*(..))")
    public void log(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Method will execute");
        joinPoint.proceed(); // delegates to the actual intercepted method
        logger.info("Method executed");
    }
}
