package com.example.simpleRewardDapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemDto {

    private Long cartItemId;
    private int quantity;
    private int price;
    private ItemDto itemDto;
}
