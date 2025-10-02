package com.sangjae.section02.javaconfig;

import com.sangjae.common.MemberDTO;
import org.springframework.context.annotation.Bean;

public class ConfigurationContext {

    // @Bean 을 통해 빈 등록
    // 별도의 설정이 없을 경우 메서드 명이 bean 이름이 되므로 필요하다면 설정
    @Bean(name = "member")
    public MemberDTO getMember(){
        return new MemberDTO(2,"user02","pass02","유관순");
    }

}
