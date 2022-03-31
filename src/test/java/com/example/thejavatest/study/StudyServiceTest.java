package com.example.thejavatest.study;

import com.example.thejavatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

public class StudyServiceTest {
    @Test
    void createStudyService() {
//        MemberService memberService = new MemberService() {
//            @Override
//            public Optional<Memeber> findById(Long memberId) {
//                return Optional.empty();
//            }
//        };

        // 생성하고자 하는 인스턴스의 클래스 타입을 넣어주면 인터페이스가 필요로하는 구현체들을 임의로 생성해줌
        MemberService memberService = Mockito.mock(MemberService.class);

        StudyRepository studyRepository = Mockito.mock(StudyRepository.class);

        // 인터페이스만 있고 구현체는 없는 클래스로 인스턴스를 생성할 때 오류 -> Mock 사용
        StudyService studyService = new StudyService(memberService, studyRepository);

        assertNotNull(studyService);

    }
}
