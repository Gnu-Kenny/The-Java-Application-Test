package com.example.thejavatest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {
    @Test
    void create(){
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create");

    }

    @Test
    void create1() {
        System.out.println("create1");
    }

    @BeforeAll // 테스트전 딱 한번, 반드시 static를 붙여야함
    static void beforeAll() {
        System.out.println("before all");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("beforeEach");
    }

    @AfterEach
    void afterEach() {
        System.out.println("afterEach");
    }
}
