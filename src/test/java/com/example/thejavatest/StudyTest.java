package com.example.thejavatest;

import net.bytebuddy.dynamic.TargetType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.Matchers.*;

import java.time.Duration;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import static org.hamcrest.MatcherAssert.assertThat;

class StudyTest {
    @Test
    @DisplayName("스터디 만들기")
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_11})
    @DisabledOnOs(value = {OS.MAC})
    void create_new_study(){
        // 조건문
        String test_env = System.getenv("TEST_ENV");

        assumeTrue("LOCAL".equalsIgnoreCase(test_env));

        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
            System.out.println("LOCAL");
            Study actual = new Study(10);
            assertThat(actual.getLimit(), is(greaterThan(0)));
        });
        assumingThat("GLOBAL".equalsIgnoreCase(test_env), () -> {
            System.out.println("GLOBAL");
            Study actual = new Study(100);
            assertThat(actual.getLimit(), is(greaterThan(0)));
        });
        System.out.println("test successfully");
    }

    @FastTest
    @DisabledOnOs(OS.OTHER)
    @DisplayName("스터디 다시 만들기")
    void create_new_study_again() {
        System.out.println(System.getProperty("os.name").toLowerCase());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        assertEquals("limit은 0보다 더 커야한다.", exception.getMessage());
    }

    @Test
    @DisplayName("스터디 만들기 중 타임아웃 발생")
    void create_new_study_timeout() {
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(300);
        });
        //assertTimeoutPreemptively 파라미터 내부에 ThreadLocal을 사용하는 코드가 있으면 예상치 못한 에러가 발생할 수 있다.
        //JUnit 문서 참고) 스프링 트랙잭션 처리는 ThreadLocal을 기본으로 한다.
        //              트랙잭션 설정이 테스트에서 적용이 제대로 이루어지지 않는다면 롤백되어야 할 상황에서 디비 반영이 되는 에러가 발생할 수 있다.
        //              -> 트랙잭션 설정을 가지고 있는 코드와 별개의 스레드로 위의 테스트 코드를 실행하게 되기 때문
    }

    @DisplayName("스터디 반복해서 만들기 - 동일한 파라미터")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo){
        System.out.println("test " + repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
    }
    @DisplayName("스터디 반복해서 만들기 - 다른 파라미터")
    @ParameterizedTest(name = "{index} message={0}")
    @ValueSource(ints = {10, 20, 40})
    void parameterizedTest(@ConvertWith(StudyConverter.class) Study study){
        System.out.println(study.getLimit());
    }

    static class StudyConverter extends SimpleArgumentConverter {


        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }
//
//    @BeforeAll // 테스트전 딱 한번, 반드시 static를 붙여야함
//    static void beforeAll() {
//        System.out.println("before all");
//    }
//
//    @AfterAll
//    static void afterAll() {
//        System.out.println("after all");
//    }
//
//    @BeforeEach
//    void beforeEach() {
//        System.out.println("beforeEach");
//    }
//
//    @AfterEach
//    void afterEach() {
//        System.out.println("afterEach");
//    }
}
