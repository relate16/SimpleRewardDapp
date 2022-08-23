package com.example.simpleRewardDapp.dto;

import com.example.simpleRewardDapp.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long cartId;
    private String username;
    private List<CartItemDto> cartItemDtos = new ArrayList<>();

    public CartDto(Long cartId, String username) {
        this.cartId = cartId;
        this.username = username;
    }
}
