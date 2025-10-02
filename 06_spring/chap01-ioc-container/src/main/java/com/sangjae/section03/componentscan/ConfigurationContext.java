package com.sangjae.section03.componentscan;

import org.springframework.context.annotation.ComponentScan;

// @ComponentScan : @Component 어노테이션이 붙은 클래스를 스캔해서 빈 등록
// basePackage 는 기입하지않으면 default가 현재 패키지 기준이 된다.
@ComponentScan(basePackages = "com.sangjae")
public class ConfigurationContext {
}
