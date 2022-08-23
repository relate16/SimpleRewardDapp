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
public class Partner {
    @Id
    @GeneratedValue
    @Column(name = "partner_id")
    private Long id;
    private String name;

    public Partner(String name) {
        this.name = name;
    }
}
