package com.ohgiraffers.mapping.section03.subsection02.compositekey;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class CartRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Cart cart) {
        entityManager.persist(cart);
    }


}
