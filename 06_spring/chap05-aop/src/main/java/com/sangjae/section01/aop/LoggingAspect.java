package com.sangjae.section01.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Aspect // Aspect(분리 된 횡단 관심사) : Point-cut (삽입시점) + Advice (부가코드)
public class LoggingAspect {

    @Pointcut("execution(* com.sangjae.section01.aop.*Service.*(..))")
    public void logPointCut(){}

    // advice (부가코드)

    // Before : 핵심 기능
    @Before("LoggingAspect.logPointCut()")
    public void logBefore(JoinPoint joinPoint){
        // joinPoint : 포인트 컷으로 패치한 실행 시점으로 메소드명 , 인수 값 등의 정보에 접근가능
        System.out.println("before joinPoint.getTarget() : " + joinPoint.getTarget());
        System.out.println("before joinPoint.getSignature() : " + joinPoint.getSignature());

        if(joinPoint.getArgs().length > 0){
            System.out.println("before joinPoint.getArgs()[0] : " + joinPoint.getArgs()[0] );
        }

    }


    // 핵심 기능 수행 후 -> 정상수행 or 오류발생 모두 수행
    @After("logPointCut()") // 같은 클래스인 경우 클래스명 생략 가능 , 다른패키지 이면 패키지 명까지 정의
    public void longAfter(JoinPoint joinPoint){
        // joinPoint : 포인트 컷으로 패치한 실행 시점으로 메소드명 , 인수 값 등의 정보에 접근가능
        System.out.println("after joinPoint.getTarget() : " + joinPoint.getTarget());
        System.out.println("after joinPoint.getSignature() : " + joinPoint.getSignature());

        if(joinPoint.getArgs().length > 0){
            System.out.println("after joinPoint.getArgs()[0] : " + joinPoint.getArgs()[0] );
        }

    }


    // 메소드 정상 수행후 반환값을 받아올 수 있다
    @AfterReturning(pointcut = "logPointCut()", returning = "result")
    public void logAfterReturn(JoinPoint joinPoint, Object result){

        System.out.println("after returning result : " + result);
        if(result instanceof Map){
            ((Map<Long,MemberDTO>)result).put(100L,new MemberDTO(100L,"반환값"));
        }

    }


    // 메소드 정상 수행후 반환값을 받아올 수 있다
    @AfterThrowing(pointcut = "logPointCut()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception){

        System.out.println("after throwing exception : " + exception);

    }


    //Around : 핵심 기능의 시작과 끝을 감싸고 동작
    @Around("logPointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around before : " + joinPoint.getSignature());

        Object result = joinPoint.proceed(); // 원본 조인 포인트를 실행하는 코드

        System.out.println("around after : " + joinPoint.getSignature());

        return result;
    }















}
