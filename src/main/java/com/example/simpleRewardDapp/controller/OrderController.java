package com.example.simpleRewardDapp.controller;

import com.example.simpleRewardDapp.dto.CartDto;
import com.example.simpleRewardDapp.dto.ItemDto;
import com.example.simpleRewardDapp.dto.OrderDto;
import com.example.simpleRewardDapp.dto.OrderItemDto;
import com.example.simpleRewardDapp.entity.*;
import com.example.simpleRewardDapp.repository.*;
import com.example.simpleRewardDapp.service.MemberService;
import com.example.simpleRewardDapp.service.OrderItemService;
import com.example.simpleRewardDapp.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final MemberService memberService;


    @PostMapping("/orders/cash")
    public Result ordersWithCash(@RequestBody OrderParam orderParam) {

        Member member = getMember(orderParam);
        Cart cart = getCart(member);

        Order order = orderService.createOrder(member);

        for (OrderItemDto orderitemdto : orderParam.getOrderItemDtos()) {
            Item item = getItem(orderitemdto.getItemDto());
            OrderItem orderItem =
                    orderItemService.createOrderItem(item, cart, orderitemdto.getQuantity(), item.getPrice(), order);
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

        OrderDto orderDto = getOrderDto(member, order);
        return new Result<OrderDto>(orderDto);
    }

    @PostMapping("/orders/point")
    public Result ordersWithPoint(@RequestBody OrderParam orderParam) {
        Member member = getMember(orderParam);
        Cart cart = getCart(member);

        Order order = orderService.createOrder(member);

        for (OrderItemDto orderItemdto : orderParam.getOrderItemDtos()) {
            Item item = getItem(orderItemdto.getItemDto());
            orderItemService.createOrderItem(item, cart, orderItemdto.getQuantity(), item.getPrice(), order);
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

        OrderDto orderDto = getOrderDto(member, order);
        return new Result<OrderDto>(orderDto);
    }

    private OrderDto getOrderDto(Member member, Order order) {
        return new OrderDto(order.getId(), member.getUsername(), order.getSavedPoint(), order.getTotalPrice(),
                order.getOrderItems().stream()
                        .map(x -> new OrderItemDto(x.getId(), x.getQuantity(),
                                new ItemDto(x.getItem().getId(), x.getItem().getName(), x.getItem().getQuantity())
                                ))
                        .collect(Collectors.toList()));
    }

    private Item getItem(ItemDto itemdto) {
        Optional<Item> findItem = itemRepository.findByName(itemdto.getName());
        return findItem.orElseThrow(() -> new RuntimeException("해당 아이템이 없습니다"));
    }

    private Cart getCart(Member member) {
        Optional<Cart> findCart = cartRepository.findByMemberId(member.getId());
        return findCart.orElseThrow(() -> new RuntimeException("해당 장바구니가 없습니다."));
    }

    private Member getMember(OrderParam orderParam) {
        Optional<Member> findMember = memberRepository.findByUsername(orderParam.getUsername());
        return findMember.orElseThrow(() -> new RuntimeException("해당 사용자가 없습니다."));
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
