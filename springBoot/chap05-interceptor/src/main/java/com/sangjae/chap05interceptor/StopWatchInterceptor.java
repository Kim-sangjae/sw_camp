package com.sangjae.chap05interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class StopWatchInterceptor implements HandlerInterceptor {

    // Interceptor 는 스프링 컨테이너에 존재하는 빈을 의존성 주입 받을 수 있다
    private final MenuService menuService;

    public StopWatchInterceptor(MenuService menuService){
        this.menuService = menuService;
    }

    // 전처리 메서드
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preCompletion 호출");

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime",startTime);
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postCompletion 호출");

        long endTime = System.currentTimeMillis();
        long startTime = (long) request.getAttribute("startTime");
        // 후처리 메서드에서는 modelAndView 객체를 가공할 수 있다
        modelAndView.addObject("interval",endTime-startTime);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion 호출");
        menuService.method();
    }
}
