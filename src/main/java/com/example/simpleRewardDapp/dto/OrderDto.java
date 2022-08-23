package com.example.simpleRewardDapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long orderId;
    private String username;
    private int savedPoint;
    private int totalPrice;
    private List<OrderItemDto> orderItemDtos = new ArrayList<>();


}
