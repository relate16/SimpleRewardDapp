package com.example.simpleRewardDapp.service;

import com.example.simpleRewardDapp.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

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
