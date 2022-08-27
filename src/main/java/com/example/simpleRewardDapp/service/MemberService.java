package com.example.simpleRewardDapp.service;

import com.example.simpleRewardDapp.entity.Member;
import com.example.simpleRewardDapp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member getMember(String username) {
        Optional<Member> findMember = memberRepository.findByUsername(username);
        return findMember.orElseThrow(() -> new RuntimeException("해당 사용자가 없습니다."));
    }

    @Transactional
    public void useCash(Member member, int price) {
        member.useCash(price);
    }

    @Transactional
    public void usePoint(Member member, int price) {
        member.usePoint(price);
    }

    @Transactional
    public void savePoint(Member member, int point) {
        member.savePoint(point);
    }

}
