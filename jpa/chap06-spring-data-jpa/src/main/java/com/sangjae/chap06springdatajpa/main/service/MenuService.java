package com.sangjae.chap06springdatajpa.main.service;

import com.sangjae.chap06springdatajpa.main.dto.CategoryDTO;
import com.sangjae.chap06springdatajpa.main.dto.MenuDTO;
import com.sangjae.chap06springdatajpa.main.entity.Category;
import com.sangjae.chap06springdatajpa.main.entity.Menu;
import com.sangjae.chap06springdatajpa.main.repository.CategoryRepository;
import com.sangjae.chap06springdatajpa.main.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public MenuDTO findMenuByCode(int menuCode) {
//        Optional<Menu> menu = menuRepository.findById(menuCode);
//        Menu menu1 = menu.orElseThrow();
        Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new);
        // DTO 와 Entity 분리
        // DTO 는 프레젠테이션 계층과 교환을 위한 객체로
        // 도메인 로직을 담고있는 Entity와 분리하여 사용하므로써
        // 보안 , 성능 , 유지보수 측면에서 이점을 얻을 수 있다
        // 1. 보안 : 민감 데이터/ 과도 데이터 노출 , 과도한 바인딩 공격 방지
        // 2. 성능 : 데이터 전송 최적화 , 지연 로딩 문제 완화 , 쿼리 최적화
        // 3. 유지보수 : 명확한 계층 분리(비즈니스 로직 수행이나 도메인 모델 변경등의 영향을 x)
        //              도메인 로직의 캡슐화(Entity 관련 비즈니스 로직은 외부에 노출 x

        // ModelMapper 라이브러리를 통해 entity to dto 변환 처리

        return modelMapper.map(menu, MenuDTO.class);
    }


    // 2. findAll(Sort)
    public List<MenuDTO> findMenuList() {
        List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());
        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).toList();
    }



    // 3. 페이징
    public Page<MenuDTO> findMenuList(Pageable pageable) {

        /* page 파라미터가 Pageable의 number 값으로 넘어오는데
         * 해당 값이 조회시에는 인덱스 기준이 되어야 해서 -1 처리가 필요하다.
         * */
        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("menuCode").descending()
        );

        Page<Menu> menuList = menuRepository.findAll(pageable);

        return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));
    }


    // 4. QueryMethod 테스트
    public List<MenuDTO> findByMenuPrice(Integer menuPrice) {
//        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice);
//        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);
        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(
                menuPrice, Sort.by("menuName"));
//        List<Menu> menuList = menuRepository.findByMenuNameContaining("돌");

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).toList();
    }



    // 5. JPQL or NativeQuery 설정
    public List<CategoryDTO> findAllCategory() {
        List<Category> categoryList = categoryRepository.findAllCategory();
        return categoryList.stream()
                .map(category -> modelMapper.map(category,CategoryDTO.class))
                .toList();
    }

    // 6. save: 엔티티 저장
    @Transactional
    public void registMenu(MenuDTO menu) {
        // DTO to Entity 빈환 후 저장
        menuRepository.save(modelMapper.map(menu,Menu.class));
    }


    // 수정 : 엔티티 조회 후 객체의 필드 값 수정
    @Transactional
    public void modifyMenu(MenuDTO menu) {
        Menu foundMenu = menuRepository.findById(menu.getMenuCode())
                .orElseThrow(IllegalArgumentException::new);
        // setter 메소드를 작성하지 않았으므로 필요한 기능에 맞는 메서드를 별도로 구현
        foundMenu.modifyMenuName(menu.getMenuName());
    }

    @Transactional
    public void deleteMenu(Integer menuCode) {
        menuRepository.deleteById(menuCode);
    }
}
