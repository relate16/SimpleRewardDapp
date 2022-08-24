package com.example.simpleRewardDapp.service;

import com.example.simpleRewardDapp.entity.Cart;
import com.example.simpleRewardDapp.entity.Member;
import com.example.simpleRewardDapp.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Cart getCart(Member member) {
        Optional<Cart> findCart = cartRepository.findByMember(member);
        return findCart.orElseThrow(() -> new RuntimeException("해당 장바구니가 없습니다."));
    }
}
