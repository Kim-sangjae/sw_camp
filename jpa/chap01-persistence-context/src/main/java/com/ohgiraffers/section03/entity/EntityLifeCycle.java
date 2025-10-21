package com.ohgiraffers.section03.entity;

import com.ohgiraffers.section01.entitymanager.EntityManagerGenerator;
import jakarta.persistence.EntityManager;

public class EntityLifeCycle {

    private EntityManager entityManager;

    public Menu findMenuByMenuCode(int menuCode){
        entityManager = EntityManagerGenerator.getInstance();

        return entityManager.find(Menu.class,menuCode);
    }

    public EntityManager getManagerInstance() {
        return entityManager;
    }


}
