package com.example.simpleRewardDapp.repository;

import com.example.simpleRewardDapp.entity.Cart;
import com.example.simpleRewardDapp.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByMember(Member member);
}
