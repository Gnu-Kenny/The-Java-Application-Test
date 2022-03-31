package com.example.thejavatest.member;

import com.example.thejavatest.domain.Memeber;

import java.util.Optional;


public interface MemberService {
    Optional<Memeber> findById(Long memberId);
}
