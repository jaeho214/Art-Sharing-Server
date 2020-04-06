package kr.ac.skuniv.artsharing.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@Aspect
public class ExceptionLogAop {

    @AfterThrowing(value = "execution(* kr.ac.skuniv.artsharing.controller.*.*.*(..)) || execution(* kr.ac.skuniv.artsharing.controller.*.*(..))", throwing = "e")
    public void printExceptionLog(JoinPoint jp, Exception e){
        log.warn("=================  Exception Log Start  =================");
        log.warn("| Exception Time => {}", LocalDateTime.now());
        log.warn("| Exception Method => {}", jp.getSignature().getName());
        log.warn("| Exception Message => {}", e.getMessage());
        log.warn("=================   Exception Log End   =================");
    }
}
