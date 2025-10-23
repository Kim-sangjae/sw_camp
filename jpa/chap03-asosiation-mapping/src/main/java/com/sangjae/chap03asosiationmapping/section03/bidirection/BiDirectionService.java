package com.sangjae.chap03asosiationmapping.section03.bidirection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BiDirectionService {

    private BiDirectionRepository biDirectionRepository;

    public BiDirectionService(BiDirectionRepository biDirectionRepository) {
        this.biDirectionRepository = biDirectionRepository;
    }

    public Menu findMenu(int menuCode) {
        return biDirectionRepository.findMenu(menuCode);
    }

    @Transactional
    public Category findCategory(int categoryCode) {
        Category foundCategory
                = biDirectionRepository.findCategory(categoryCode);
        System.out.println("[ Category menuList ] "
                + foundCategory.getMenuList());

        // 양방향 참조를 구현하면 양방향 그래프 탐색이 가능하다
        // Category -> Menu -> Category -> Menu
        List<Menu> menuList = foundCategory.getMenuList();
        for (Menu menu : menuList) {
            System.out.println(menu.getCategory());
        }

        return foundCategory;
    }

    @Transactional
    public void registMenu(Menu menu) {
        biDirectionRepository.saveMenu(menu);
    }

    @Transactional
    public void registCategory(Category category) {
        biDirectionRepository.saveCategory(category);
    }
}
