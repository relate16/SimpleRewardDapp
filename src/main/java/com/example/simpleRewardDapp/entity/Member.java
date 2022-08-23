package com.example.simpleRewardDapp.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;
    private int cash;
    private int point;

    public Member(String username, int cash, int point) {
        this.username = username;
        this.cash = cash;
        this.point = point;
    }

    // 비지니스 로직
    public void useCash(int cash) {
        this.cash -= cash;
    }

    public void usePoint(int point) {
        this.point -= point;
    }

    public void savePoint(int point) {
        this.point += point;
    }

}
