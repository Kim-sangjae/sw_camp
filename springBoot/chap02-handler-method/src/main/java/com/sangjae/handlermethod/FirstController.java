package com.sangjae.handlermethod;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/first/*")
@SessionAttributes({"id","key"})
public class FirstController {


    // 핸들러 메서드의 반환값이 void인 경우 요청주소가 곧 view name 이 된다.
    @GetMapping("/regist")
    public void regist(){}

    // 1. WebRequest 로 요청 파라미터 전달 받기
    // HttpServletRequest / Response 등 도 매개변수에 선언해서 사용하는 것은 가능하지만
    // WebRequest 가 Servlet 기술에 종속적이지 않아 Spring 기반의 프로젝트에서 더 자주 사용된다
    @PostMapping("/regist")
    public String registMenu(WebRequest request, Model model){
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int category = Integer.parseInt(request.getParameter("categoryCode"));

        String message = name + "를 신규 메뉴 목록의 " + category + "번 카테고리에 " +
                price + "원 으로 등록하였습니다.";

        model.addAttribute("message",message);

        return "first/messagePrinter";
    }



    // 2. @RequestParam
    // 요청 파라미터를 매핑하여 핸들러 메소드 호출 시 값을 넣어주는 어노테이션
    // 파람 값과 변수명을 다르게 쓰고 싶으면 정의해야한다
    // @RequestParam("name") String modifyName
    // 파람명과 변수명이 같으면  @RequestParam 을 생략도 가능
    @GetMapping("/modify")
    public void modify(){}

    @PostMapping("/modify")
    public String modifyMenu(
            // 파람 값과 변수명을 다르게 쓰고 싶으면 정의해야한다
            // @RequestParam("name") String modifyName
            @RequestParam("name") String modifyName,
            @RequestParam(defaultValue = "0") int price,
            @RequestParam(required = false) Integer categoryCode,
            Model model
            ){

        String message = "메뉴의 가격을 " + price + "원 "
        + categoryCode +"번 카테고리로 변경하였습니다";

        model.addAttribute("message",message);
        return "first/messagePrinter";
    }




    // 3. @ModelAttribute
    // 어노테이션을 생략하고 작성 할수도 있으나 name 설정을 할 경우 어노테이션이 필요하다
    // 작성 된 name 속성을 통해 view에서 해닫 데이터를 사용할 수 있다.
    @GetMapping("/search")
    public void search(){};


    @PostMapping("/search")
    public String searchMenu(@ModelAttribute("menu") MenuDTO menuDTO){
        System.out.println(menuDTO.getName());
        System.out.println(menuDTO.getPrice());
        System.out.println(menuDTO.getCategoryCode());

        return "first/searchResult";
    }


    // 4. @SessionAttribute
    // HttpSession 을 전달 받는 것도 가능 하지만 Servlet 종속적이므로
    // Spring에서 제공하는 기능을 사용하는것을 권장
    // @SessionAttribute("key") '와 같이 지정하면
    // Model에 해당 key가 추가 될 때 Session에도 자동으로 등록된다
    @GetMapping("/login")
    public void login(){}


    @PostMapping("/login")
    public String loginTest(String id , String pwd, Model model){

        model.addAttribute("id",id);
        model.addAttribute("pwt",pwd);

        return "first/loginResult";
    }


    // @SessionAttribute 만료 시키기
    // SessionStatus 라는 세션의 상태를 관리하는 객체의 setComplete 메서드로 세션을 만료
    // HttpSession 의 invalidate 메서드는 호출해도 세션값 만료 불가능
    @GetMapping("/logout1")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "first/loginResult";
    }

    @GetMapping("/logout2")
    public String logout(SessionStatus sessionStatus){
        sessionStatus.setComplete();
        return "first/loginResult";
    }



    // 5. @RequestBody
    @GetMapping("/body")
    public void body(){}

    @PostMapping("/body")
    public void requestBody(
            @RequestBody String body,
            @RequestHeader("content-type") String contentType,
            @CookieValue("JSESSIONID") String sessionId
    ){
        System.out.println(body);
        System.out.println(contentType);
        System.out.println(sessionId);
    }
}
