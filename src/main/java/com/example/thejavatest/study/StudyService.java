package com.example.thejavatest.study;

import com.example.thejavatest.domain.Memeber;
import com.example.thejavatest.domain.Study;
import com.example.thejavatest.member.MemberService;

import java.util.Optional;


public class StudyService {
    private final MemberService memberService;

    private final StudyRepository repository;

    public StudyService(MemberService memberService, StudyRepository repository) {
        assert memberService != null;
        assert repository != null;
        this.memberService = memberService;
        this.repository = repository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Memeber> member = memberService.findById(memberId);
        study.setOwner(member.orElseThrow(() -> new IllegalArgumentException("Member doesn't exist for id: '" + memberId +"'"));
        return repository.save(study);
    }


}
