package com.sangjae.requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MethodMappingTestController {

    // 1. Http Method 방식 미지정
    @RequestMapping("/menu/regist")
    public String menuRegister(Model model){
        // 반환하고자하는 view 의 경로를 포함한 이름을 작성
        // resource/templates 하위부터의 경로를 작성

        // Model 객체에 attribute를 key , value 값으로 추가하면
        // view에서 사용 가능하다 (타임리프 th=${message})
        model.addAttribute("message","신규 메뉴 등록 핸들러 메서드 호출");
        return "MappingResult";
    }

    // 2. HTTP Method 방식 지정
    @RequestMapping(value ="/menu/modify", method = RequestMethod.GET)
    public String menuModify(Model model){
        model.addAttribute("message","GET 방식의 메뉴 수정 핸들러 메소드");
        return "mappingResult";
    }


    // 3. HTTP Method 전용 어노테이션
    @GetMapping("/menu/delete")
    public String getMenuDelete(Model model){
        model.addAttribute("message","GET 방식의 메뉴삭제 핸들러 메소드");
        return "mappingResult";
    };

    @PostMapping("/menu/delete")
    public String postMenuDelete(Model model){
        model.addAttribute("message","POST 방식의 메뉴 삭제 핸들러 메소드");
        return "mappingResult";
    };



}
