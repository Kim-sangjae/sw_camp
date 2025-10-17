package com.sangjae.viewresolver;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ResolverController {

    @GetMapping("/string")
    public String stringReturning(Model model){
        // Model : view 에서 표현 되어야 하는 동적인 데이터를 담는 용도로 사용하는 객체
        model.addAttribute("forwardMessage","문자열로 뷰 이름 반환");

        // String 타입으로 반환할 경우 논리적인 뷰 이름을 리턴한다
        // ViewResolver가 prefix/suffix 를 합쳐서 물리적인 뷰를 선택한다.
        // prefix : 경로  , suffix : 확장자명
        return "result";
    }

    @GetMapping("/string-redirect")
    public String stringRedirect(){
        // redirect: 를 붙인 뒤 redirect할 주소 값을 작성한다.
        return "redirect:/";
    }


    // Redirect 시 request scope의 데이터는 공유 되지않는다
    // session scope에 너무 많은 데이터를 저장하는 것은 서버 성능에 영향을 준다
    // RedirectAttribute 객체를 통해 잠시 세션에 저장했다가 redirect 후 세션에서 제거되게 할수있다
    @GetMapping("/string-redirect-attr")
    public String stringRedirectAttr(RedirectAttributes rttr){
        rttr.addFlashAttribute("flashMessage","리다이랙트 attr 사용");
        return "redirect:/";
    }


    @GetMapping("/model-and-view")
    public ModelAndView modelAndView(ModelAndView mv){

        // Model 개체에 attribute 저장
        mv.addObject("forwardMessage","ModelAndView를 사용");

        // view 객체에 논리적 뷰 이름 설정
        mv.setViewName("result");

        return mv;
    }

}
