package com.example.simpleRewardDapp.service;

import com.example.simpleRewardDapp.dto.OrderItemDto;
import com.example.simpleRewardDapp.entity.*;
import com.example.simpleRewardDapp.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    private final CartItemService cartItemService;
    private final ItemService itemService;
    private final OrderService orderService;

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
    public void createOrderItemsCash(List<OrderItemDto> orderItemDtos, Cart cart, Order order) {
        for (OrderItemDto orderItemDto : orderItemDtos) {
            Item item = itemService.getItem(orderItemDto.getItemDto().getName());

            OrderItem orderItem = createOrderItem(item, cart, orderItemDto.getQuantity(), item.getPrice(), order);
            orderService.plusSavePoint(order, orderItem, item);
        }
    }

    @Transactional
    public void createOrderItemsPoint(List<OrderItemDto> orderItemDtos, Cart cart, Order order) {
        for (OrderItemDto orderItemDto : orderItemDtos) {
            Item item = itemService.getItem(orderItemDto.getItemDto().getName());
            createOrderItem(item, cart, orderItemDto.getQuantity(), item.getPrice(), order);
        }
    }


    @Transactional
    public void createOrderItemsInCart(Cart cart, List<CartItem> cartItems, Order order) {
        for (CartItem cartItem : cartItems) {
            createOrderItem(cartItem.getItem(), cart, cartItem.getQuantity(), cartItem.getPrice(), order);
            //카트에서 제거되면 아이템 재고 복구
            cartItemService.deleteCartItem(cartItem);
        }
    }

    @Transactional
    public void cancel(OrderItem orderItem) {
        orderItem.cancel();
    }
}
