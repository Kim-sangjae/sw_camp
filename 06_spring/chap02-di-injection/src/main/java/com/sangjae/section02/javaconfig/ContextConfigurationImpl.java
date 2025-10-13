package com.sangjae.section02.javaconfig;

import com.sangjae.common.Account;
import com.sangjae.common.MemberDTO;
import com.sangjae.common.PersonalAccount;
import org.springframework.context.annotation.Bean;

public class ContextConfigurationImpl {

    // 자바 파일로 config 설정 할때는 인터페이스 타입으로도 빈 등록 가능
    @Bean
    public Account accountGenerator(){
        return  new PersonalAccount(20,"123-4557-89");
    }


    // bean 등록에 사용된 메서드를 호출하여 의존성 주입을 처리
    @Bean
    public MemberDTO memberGenerator(){
        // 1. 생성자 주입
//        return new MemberDTO(
//                1,"홍길동","010-1234-1234","hong@gamil.com",accountGenerator()
//        );

        // 2. setter 주입
        // setter 를 통해 PersonalAccount만 설정해주었다
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setPersonalAccount(accountGenerator());
        return memberDTO;

    }

}
