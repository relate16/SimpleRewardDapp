package com.example.simpleRewardDapp.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private int quantity;
    private int price;

    // 생성자 메서드 쓰니까 protected로 해놔야 하나..?
    public OrderItem(Item item, Cart cart, int quantity, int price) {
        this.item = item;
        this.cart = cart;
        this.quantity = quantity;
        this.price = price;
    }

    // 생성자 메서드
    public static OrderItem createOrderItem(Item item, Cart cart, int quantity, int price) {
        OrderItem orderItem = new OrderItem(item, cart, quantity, price);
        item.subQuantity(quantity);
        return orderItem;
    }

    // 필드 값 접근 메서드
    public void changeOrder(Order order) {
        this.order = order;
    }

    // 비지니스 로직
    public void cancel() {
        item.addQuantity(quantity);
    }
}
