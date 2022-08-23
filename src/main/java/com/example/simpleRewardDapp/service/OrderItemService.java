package com.example.simpleRewardDapp.service;

import com.example.simpleRewardDapp.entity.Cart;
import com.example.simpleRewardDapp.entity.Item;
import com.example.simpleRewardDapp.entity.Order;
import com.example.simpleRewardDapp.entity.OrderItem;
import com.example.simpleRewardDapp.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrderItem createOrderItem(Item item, Cart cart, int quantity, int price, Order order) {
        if (item.getQuantity() - quantity < 0) {
            throw new RuntimeException("상품 재고 수량 초과입니다.");
        }
        OrderItem orderItem = OrderItem.createOrderItem(item, cart, quantity, price);
        orderItemRepository.save(orderItem);
        order.addOrderItem(orderItem);
        return orderItem;
    }

    @Transactional
    public void cancel(OrderItem orderItem) {
        orderItem.cancel();
    }
}
