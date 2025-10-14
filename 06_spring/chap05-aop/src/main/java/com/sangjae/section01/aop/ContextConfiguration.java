package com.sangjae.section01.aop;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
// proxyTargetClass=true : Proxy 객체 생성 방식으로 DGLib 방식을 사용
// target object 가 interface가 아닌 class여도 적용 가능한 방식
// @EnableAspectJAutoProxy(proxyTargetClass = true)는 **“프록시를 만들어라”**까지만 알려주고,
//**“무엇을 프록시로 만들지”**는 Spring이 AOP 설정(즉, @Aspect와 @Pointcut) 을 기준으로 자동으로 판단합니다.
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ContextConfiguration {
}
