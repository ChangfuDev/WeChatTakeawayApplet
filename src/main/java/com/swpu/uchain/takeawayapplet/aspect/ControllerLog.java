package com.swpu.uchain.takeawayapplet.aspect;

import com.swpu.uchain.takeawayapplet.VO.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @ClassName ControllerLog
 * @Author hobo
 * @Date 19-1-5 下午7:34
 **/
@Aspect
@Component
@Slf4j
public class ControllerLog {
    @Pointcut("execution(public * com.swpu.uchain.takeawayapplet.controller.*.*(..))")
    public void controller() {
    }

    @Before("controller()")
    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String method = signature.getDeclaringTypeName() + "." + signature.getName();
        log.info("-----------------------------------------------------");
        log.info("当前执行controller的方法:  " + method);
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info("参数: " + arg);
        }
    }

    @AfterReturning(pointcut = "controller()", returning = "ret")
    public void after(Object ret) {
        ResultVO result = (ResultVO) ret;
        if (result != null && result.getCode() != 0) {
            log.error(result.getMsg());
        }
        log.info("controller返回参数：" + result);
        log.info("-----------------------------------------------------");
    }
}
