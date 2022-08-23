package com.example.simpleRewardDapp.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int quantity;
    private int pointPercent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private Partner partner;

    public Item(String name, int price, int quantity, int pointPercent, Partner partner) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.pointPercent = pointPercent;
        this.partner = partner;
    }

    // 비지니스 로직
    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void subQuantity(int quantity) {
        int subQuantity = this.quantity - quantity;
        if (subQuantity < 0) {
            throw new RuntimeException("재고가 없습니다.");
        } else {
            this.quantity = subQuantity;
        }
    }
}
