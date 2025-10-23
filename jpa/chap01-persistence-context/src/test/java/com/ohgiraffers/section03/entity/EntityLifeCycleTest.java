package com.ohgiraffers.section03.entity;

import com.ohgiraffers.section01.entitymanager.EntityManagerGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EntityLifeCycleTest {

    private EntityLifeCycle entityLifeCycle;

    @BeforeEach
    void init() {
        entityLifeCycle = new EntityLifeCycle();
    }

    @DisplayName("비영속테스트")
    @ParameterizedTest
    @ValueSource(ints = {4, 5})
    void testTransient(int menuCode) {
        // when
        Menu foundMenu = entityLifeCycle.findMenuByMenuCode(menuCode);

        Menu newMenu = new Menu(
                foundMenu.getMenuCode(),
                foundMenu.getMenuName(),
                foundMenu.getMenuPrice(),
                foundMenu.getCategoryCode(),
                foundMenu.getOrderableStatus()
                );

        EntityManager entityManager = entityLifeCycle.getManagerInstance();

        // then
        assertTrue(entityManager.contains(foundMenu));// 영속성 컨텍스트에서 관리되는 영속 상태
        assertFalse(entityManager.contains(newMenu));// 영속성 컨텍스트에서 관리되지 않는 비영속 상태
    }

    @DisplayName("다른 엔터티 매니저가 관리하는 엔터티의 영속성 테스트")
    @ParameterizedTest
    @ValueSource(ints = {4, 5})
    void testManagedOtherEntityManager(int menuCode) {
        // when
        Menu menu1 = entityLifeCycle.findMenuByMenuCode(menuCode);
        Menu menu2 = entityLifeCycle.findMenuByMenuCode(menuCode);

        // then
        assertNotEquals(menu1, menu2);
    }

    @DisplayName("같은 엔터티 매니저가 관리하는 엔터티의 영속성 테스트")
    @ParameterizedTest
    @ValueSource(ints = {4, 5})
    void testManagedSameEntityManager(int menuCode) {
        // when
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        Menu menu1 = entityManager.find(Menu.class, menuCode);
        Menu menu2 = entityManager.find(Menu.class, menuCode);

        // then
        assertEquals(menu1, menu2);
    }

    @DisplayName("준영속화 detach 테스트")
    @ParameterizedTest
    @CsvSource({"11, 1000", "12, 1000"})
    void testDetachedEntity(int menuCode, int menuPrice) {
        // when
        EntityManager entityManager = EntityManagerGenerator.getInstance();

        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        // detach : 특정 엔터티만 준영속 상태(관리하던 객체를 관리하지 않는 상태로 변경)로 만듦
        entityManager.detach(foundMenu);
        foundMenu.setMenuPrice(menuPrice);

        // then
        assertNotEquals(menuPrice, entityManager.find(Menu.class, menuCode).getMenuPrice());

    }

    @DisplayName("준영속화 detach 후 다시 영속화 테스트")
    @ParameterizedTest
    @CsvSource({"11, 1000", "12, 1000"})
    void testDetachedAndMerge(int menuCode, int menuPrice) {
        // when
        EntityManager entityManager = EntityManagerGenerator.getInstance();

        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        entityManager.detach(foundMenu);
        foundMenu.setMenuPrice(menuPrice);
        // merge : 파라미터로 넘어온 준영속 엔터티 객체의 식별자 값으로 1차 캐시에서 엔터티 객체를 조회
        // (없으면 DB에서 조회하여 1차 캐시에 저장)
        // 조회한 영속 엔터티 객체에 준영속 상태의 엔터티 객체의 값을 병합한 뒤 영속 엔터티 객체를 반환
        // 혹은 조회할 수 없는 데이터라면 새로 생성해서 반환
        entityManager.merge(foundMenu);

        // then
        assertEquals(menuPrice, entityManager.find(Menu.class, menuCode).getMenuPrice());

    }

    @DisplayName("detach 한 후 merge 한 데이터 update 테스트")
    @ParameterizedTest
    @CsvSource({"11, 하양 민트초코죽", "12, 까만 딸기탕후루"})
    void testDetachedAndMerge(int menuCode, String menuName) {
        // when
        EntityManager entityManager = EntityManagerGenerator.getInstance();

        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        entityManager.detach(foundMenu);
        foundMenu.setMenuName(menuName);

        Menu refoundMenu = entityManager.find(Menu.class, menuCode);
        entityManager.merge(foundMenu);

        // then
        assertEquals(menuName, refoundMenu.getMenuName());

    }

    @DisplayName("detach 한 후 merge 한 데이터 save 테스트")
    @Test
    void testMergeSave() {
        // when
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Menu foundMenu = entityManager.find(Menu.class, 20);
        entityManager.detach(foundMenu);
        foundMenu.setMenuName("치약맛 초코 아이스");
        foundMenu.setMenuCode(999);
        entityManager.merge(foundMenu);
        entityTransaction.commit();

        // then
        assertEquals("치약맛 초코 아이스", entityManager.find(Menu.class, 999).getMenuName());
    }

    @DisplayName("준영속화 clear 테스트")
    @ParameterizedTest
    @ValueSource(ints = {4, 5, 6})
    void testClearPersistenceContext(int menuCode) {
        //given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        //when
        /* clear
         * 영속성 컨텍스트를 초기화한다
         * = 영속성 컨텍스트 내 모든 엔티티를 준영속화 시킨다.
         */
        entityManager.clear();

        //then
        Menu expectedMenu = entityManager.find(Menu.class, menuCode);
        assertNotEquals(expectedMenu, foundMenu);
    }

    @DisplayName("준영속화 close 테스트")
    @ParameterizedTest
    @ValueSource(ints = {4, 5, 6})
    void testClosePersistenceContext(int menuCode) {
        //given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        //when
        /* close()
         * 영속성 컨텍스트를 종료한다
         * = 영속성 컨텍스트 내 모든 객체를 준영속화 시킨다.
         */
        entityManager.close();

        //then
//        Menu expectedMenu = entityManager.find(Menu.class, menuCode);
//        assertNotEquals(expectedMenu, foundMenu);
        assertThrows(
                IllegalStateException.class,
                () -> entityManager.find(Menu.class, menuCode)
        );
    }

    @DisplayName("영속성 엔티티 삭제 remove 테스트")
    @ParameterizedTest
    @ValueSource(ints = {4})
    void testRemoveEntity(int menuCode) {
        //given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        EntityTransaction transaction = entityManager.getTransaction();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        //when
        transaction.begin();

        /* remove
         * 엔티티를 영속성 컨텍스트 및 데이터베이스에서 삭제한다.
         * 단, 트랜잭션을 제어하지 않으면 데이터베이스에 영구 반영되지는 않는다.
         * 트랜잭션을 커밋하는 순간
         * 영속성 컨텍스트에서 관리하는 엔티티 객체가 데이터베이스에 반영된다.
         * */
        entityManager.remove(foundMenu);

        /* flush
         * 영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화하는 작업을 한다.
         * (등록/수정/삭제한 엔티티를 데이터베이스에 반영)
         */
        entityManager.flush();

        //then
        Menu refoundMenu = entityManager.find(Menu.class, menuCode);
        assertNull(refoundMenu);
        transaction.rollback();
    }
}