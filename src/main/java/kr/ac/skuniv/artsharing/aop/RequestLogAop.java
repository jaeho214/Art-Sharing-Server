package kr.ac.skuniv.artsharing.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
@Aspect
@Slf4j
public class RequestLogAop {
    @Pointcut("execution(* kr.ac.skuniv.artsharing.controller.*.*.*(..))")
    public void loggerPointCut(){
    }

    @Around("loggerPointCut()")
    public Object printRequestLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try{
            Object result = proceedingJoinPoint.proceed();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            String controllerName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
            String methodName = proceedingJoinPoint.getSignature().getName();

            try{
                log.warn("=================  Request Log Start  =================");
                log.warn("Request Controller => {}", controllerName);
                log.warn("Request Method => {}", methodName);
                log.warn("Request URL => {}", request.getRequestURL());
                log.warn("Request HTTP Method => {}",request.getMethod());
                log.warn("Request Log Time => {}", LocalDateTime.now());
                log.warn("=================   Request Log End   =================");
            }catch(Exception e){
                log.error("LoggerAspect error", e);
            }

            return result;
        } catch (Throwable throwable) {
            throw throwable;
        }
    }
}
