package com.sangjae.chap06springdatajpa.main.menu;

import com.sangjae.chap06springdatajpa.common.Pagination;
import com.sangjae.chap06springdatajpa.common.PagingButtonInfo;
import com.sangjae.chap06springdatajpa.main.dto.CategoryDTO;
import com.sangjae.chap06springdatajpa.main.dto.MenuDTO;
import com.sangjae.chap06springdatajpa.main.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
@Slf4j // log라는 변수명으로 logger 객체 타입의 필드가 선언되고 생성된다.
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{menuCode}")
    public String findMenuByCode(@PathVariable int menuCode, Model model) {
        MenuDTO resultMenu = menuService.findMenuByCode(menuCode);
        model.addAttribute("menu", resultMenu);
        return "menu/detail";
    }

//    @GetMapping("/list")
//    public String findMenuList(Model model){
//        List<MenuDTO> menuList = menuService.findMenuList();
//        model.addAttribute("menuList",menuList);
//        return "menu/list";
//    }

    @GetMapping("/list")
    public String findMenuList(
            @PageableDefault Pageable pageable, Model model
    ) {

        /* page -> number, size, sort 파라미터가 Pageable 객체에 담긴다. */
        log.info("pageable : {}", pageable);

        Page<MenuDTO> menuList = menuService.findMenuList(pageable);

        log.info("조회한 내용 목록 : {}", menuList.getContent());
        log.info("총 페이지 수 : {}", menuList.getTotalPages());
        log.info("총 메뉴 수 : {}", menuList.getTotalElements());
        log.info("해당 페이지에 표시 될 요소 수 : {}", menuList.getSize());
        log.info("해당 페이지에 실제 요소 수:{}",menuList.getNumberOfElements());
        log.info("첫 페이지 여부 : {}", menuList.isFirst());
        log.info("마지막 페이지 여부 : {}", menuList.isLast());
        log.info("정렬 방식 : {}", menuList.getSort());
        log.info("여러 페이지 중 현재 인덱스 : {}", menuList.getNumber());

        PagingButtonInfo paging = Pagination.getPagingButtonInfo(menuList);

        model.addAttribute("paging", paging);
        model.addAttribute("menuList", menuList);

        return "menu/list";
    }



    @GetMapping("/querymethod")
    public void queryMethod(){}


    @GetMapping("/search")
    public String findByMenuPrice(@RequestParam Integer menuPrice, Model model){
        List<MenuDTO> menuList = menuService.findByMenuPrice(menuPrice);
        model.addAttribute("menuList",menuList);
        return "menu/searchResult";
    }

    @GetMapping("/regist")
    public void registPage(){
    }

    @GetMapping("/category")
    @ResponseBody // 응답 데이터 body에 반환값을 그대로 전달하겠다는 의미(ViewResolver 사용 x)
    public List<CategoryDTO> findCategoryList(){
        return menuService.findAllCategory();
    }

    @PostMapping("/regist")
    public String registMenu(MenuDTO menu){
        menuService.registMenu(menu);
        return "redirect:/menu/list";
    }


    @GetMapping("/modify")
    public void modifyPage(){}

    @PostMapping("/modify")
    public String modifyMenu(MenuDTO menu){
        menuService.modifyMenu(menu);
        return "redirect:/menu/" + menu.getMenuCode();
    }

    @GetMapping("/delete")
    public void deletePage(){}
    @PostMapping("/delete")
    public String deleteMenu(Integer menuCode){
        menuService.deleteMenu(menuCode);
        return "redirect:/menu/list";
    }


}
