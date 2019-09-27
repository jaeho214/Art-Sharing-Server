package kr.ac.skuniv.artsharing.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
//TODO : 메소드 이름이 실행될 때 메소드 이름 출력을 aop로 적용

@Aspect
@Component
public class LoggingMethodAop {

    private static final Logger logger = LoggerFactory.getLogger(LoggingMethodAop.class);

    @Before("execution(* kr.ac.skuniv.artsharing.service.*.*.*(..)) || execution(* kr.ac.skuniv.artsharing.service.*.*(..))")
    public void writeLog(JoinPoint jp){
        logger.info("메소드 이름 : " + jp.getSignature().getName());
    }

}
