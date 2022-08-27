package com.example.simpleRewardDapp.controller;

import com.example.simpleRewardDapp.dto.OrderDto;
import com.example.simpleRewardDapp.dto.OrderItemDto;
import com.example.simpleRewardDapp.entity.Cart;
import com.example.simpleRewardDapp.entity.Member;
import com.example.simpleRewardDapp.entity.Order;
import com.example.simpleRewardDapp.service.CartService;
import com.example.simpleRewardDapp.service.MemberService;
import com.example.simpleRewardDapp.service.OrderItemService;
import com.example.simpleRewardDapp.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final MemberService memberService;
    private final CartService cartService;

    @PostMapping("/orders/cash")
    public Result ordersWithCash(@RequestBody OrderParam orderParam) {
        Member member = memberService.getMember(orderParam.getUsername());
        Cart cart = cartService.getCart(member);
        Order order = orderService.createOrder(member);

        orderItemService.createOrderItemsCash(orderParam.getOrderItemDtos(), cart, order);

        int totalPrice = orderService.calculateTotalPrice(order);

        orderService.checkCanBuyWithCash(member, order, totalPrice);

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

        orderItemService.createOrderItemsPoint(orderParam.getOrderItemDtos(),cart,order);

        int totalPrice = orderService.calculateTotalPrice(order);

        orderService.checkCanBuyWithPoint(member, order, totalPrice);

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
    @NoArgsConstructor
    @AllArgsConstructor
    static class OrderParam {
        private String username;
        private List<OrderItemDto> orderItemDtos = new ArrayList<>();
    }
}
