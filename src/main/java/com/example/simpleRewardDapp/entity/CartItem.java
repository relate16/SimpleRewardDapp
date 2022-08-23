package com.example.simpleRewardDapp.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {
    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private int quantity;
    private int price;

    public CartItem(Item item, Cart cart, int quantity, int price) {
        this.item = item;
        this.cart = cart;
        this.quantity = quantity;
        this.price = price;
    }

    //생성자 메서드
    public static CartItem createCartItem(Item item, Cart cart, int quantity, int price) {
        CartItem cartItem = new CartItem(item, cart, quantity, price);
        item.subQuantity(quantity);
        return cartItem;
    }

    // 비지니스 로직
    public void cancel() {
        item.addQuantity(quantity);
    }

}
