package com.example.simpleRewardDapp.controller;

import com.example.simpleRewardDapp.dto.ItemDto;
import com.example.simpleRewardDapp.dto.OrderDto;
import com.example.simpleRewardDapp.dto.OrderItemDto;
import com.example.simpleRewardDapp.entity.*;
import com.example.simpleRewardDapp.service.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final MemberService memberService;
    private final ItemService itemService;
    private final CartService cartService;


    @PostMapping("/orders/cash")
    public Result ordersWithCash(@RequestBody OrderParam orderParam) {

        Member member = memberService.getMember(orderParam.getUsername());
        Cart cart = cartService.getCart(member);

        Order order = orderService.createOrder(member);

        for (OrderItemDto orderItemDto : orderParam.getOrderItemDtos()) {
            Item item = itemService.getItem(orderItemDto.getItemDto().getName());
            OrderItem orderItem =
                    orderItemService.createOrderItem(item, cart, orderItemDto.getQuantity(), item.getPrice(), order);
            orderService.plusSavePoint(order, orderItem, item);
        }

        int totalPrice = orderService.calculateTotalPrice(order);

        if (member.getCash() < totalPrice) {
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                orderItemService.cancel(orderItem);
            }
            throw new RuntimeException("보유한 금액이 적습니다.");
        }

        memberService.useCash(member, totalPrice);
        memberService.savePoint(member, order.getSavedPoint());

        OrderDto orderDto = orderService.getOrderDto(member, order);
        return new Result<OrderDto>(orderDto);
    }

    @PostMapping("/orders/point")
    public Result ordersWithPoint(@RequestBody OrderParam orderParam) {
        Member member = memberService.getMember(orderParam.getUsername());

        Cart cart = cartService.getCart(member);
        Order order = orderService.createOrder(member);

        for (OrderItemDto orderItemDto : orderParam.getOrderItemDtos()) {
            Item item = itemService.getItem(orderItemDto.getItemDto().getName());
            orderItemService.createOrderItem(item, cart, orderItemDto.getQuantity(), item.getPrice(), order);
        }

        int totalPrice = orderService.calculateTotalPrice(order);

        if (member.getPoint() < totalPrice) {
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                orderItemService.cancel(orderItem);
            }
            throw new RuntimeException("보유한 포인트가 적습니다.");
        }

        memberService.usePoint(member, totalPrice);

        OrderDto orderDto = orderService.getOrderDto(member, order);
        return new Result<OrderDto>(orderDto);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    /**
     * 2개 클래스 하나로 통합하는 클래스 임시 생성
     */
    @Data
    @AllArgsConstructor
    static class OrderParam {
        private String username;
        private List<OrderItemDto> orderItemDtos;
    }

}
