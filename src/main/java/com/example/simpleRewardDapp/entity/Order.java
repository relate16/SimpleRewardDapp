package com.example.simpleRewardDapp.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private int totalPrice;
    private int savedPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(Member member) {
        this.member = member;
    }

    //연관 관계 편의 메서드
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.changeOrder(this);
    }

    // 비지니스 메서드
    public int calculateTotalPrice() {
        totalPrice = 0;
        List<OrderItem> orderItems = getOrderItems();
        for (OrderItem orderItem : orderItems) {
            int result = orderItem.getPrice() * orderItem.getQuantity();
            totalPrice += result;
        }
        return totalPrice;
    }

    public void plusSavedPoint(int savedPoint) {
        this.savedPoint += savedPoint;
    }
}
