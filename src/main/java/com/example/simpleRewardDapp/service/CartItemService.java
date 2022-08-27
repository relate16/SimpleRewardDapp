package com.example.simpleRewardDapp.service;

import com.example.simpleRewardDapp.dto.CartItemDto;
import com.example.simpleRewardDapp.dto.ItemDto;
import com.example.simpleRewardDapp.entity.Cart;
import com.example.simpleRewardDapp.entity.CartItem;
import com.example.simpleRewardDapp.entity.Item;
import com.example.simpleRewardDapp.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;


    @Transactional
    public CartItem createCartItem(Item item, Cart cart, int quantity, int price) {
        if (item.getQuantity() - quantity < 0) {
            throw new RuntimeException("상품 재고 수량 초과입니다.");
        }
        CartItem cartItem = CartItem.createCartItem(item, cart, quantity, price);
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    @Transactional
    public void deleteCartItem(CartItem cartItem) {
        cartItem.getItem().addQuantity(cartItem.getQuantity());
        cartItemRepository.delete(cartItem);
    }

    @Transactional
    public void deleteCartItems(List<CartItemDto> cartItemDtos) {
        for (CartItemDto cartItemDto : cartItemDtos) {
            List<CartItem> cartItems = cartItemRepository.findByItemName(cartItemDto.getItemDto().getName());
            deleteCartItem(cartItems.get(0));
        }
    }

    public List<CartItemDto> getCartItemDtos(Cart cart) {
        List<CartItem> cartItems = cartItemRepository.findCartItemsByCartId(cart.getId());
        List<CartItemDto> cartItemDtos =
                cartItems.stream().map(x -> new CartItemDto(x.getId(), x.getQuantity(), x.getPrice(),
                                new ItemDto(x.getItem().getId(), x.getItem().getName(), x.getItem().getQuantity())))
                        .collect(Collectors.toList());
        return cartItemDtos;
    }

}
