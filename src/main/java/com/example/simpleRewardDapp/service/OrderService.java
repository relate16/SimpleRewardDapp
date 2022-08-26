package com.example.simpleRewardDapp.service;

import com.example.simpleRewardDapp.dto.ItemDto;
import com.example.simpleRewardDapp.dto.OrderDto;
import com.example.simpleRewardDapp.dto.OrderItemDto;
import com.example.simpleRewardDapp.entity.Item;
import com.example.simpleRewardDapp.entity.Member;
import com.example.simpleRewardDapp.entity.Order;
import com.example.simpleRewardDapp.entity.OrderItem;
import com.example.simpleRewardDapp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(Member member) {
        Order order = new Order(member);
        orderRepository.save(order);
        return order;
    }

    @Transactional
    public int calculateTotalPrice(Order order) {
        int result = order.calculateTotalPrice();
        return result;
    }

    @Transactional
    public int plusSavePoint(Order order, OrderItem orderItem, Item item) {
        int savedPoint = (int) (orderItem.getPrice() * orderItem.getQuantity() * (item.getPointPercent()/100.0));
        order.plusSavedPoint(savedPoint);
        return order.getSavedPoint();
    }

    public OrderDto getOrderDto(Member member, Order order) {
        return new OrderDto(order.getId(), member.getUsername(), order.getSavedPoint(), order.getTotalPrice(),
                order.getOrderItems().stream()
                        .map(x -> new OrderItemDto(x.getId(), x.getQuantity(),
                                new ItemDto(x.getItem().getId(), x.getItem().getName(), x.getItem().getQuantity())
                        ))
                        .collect(Collectors.toList()));
    }
}
