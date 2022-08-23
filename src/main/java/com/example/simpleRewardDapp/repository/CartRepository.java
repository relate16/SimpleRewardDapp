package com.example.simpleRewardDapp.repository;

import com.example.simpleRewardDapp.entity.Cart;
import com.example.simpleRewardDapp.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c join c.member m where m.id = :memberId")
    Optional<Cart> findByMemberId(@Param("memberId") Long memberId);
}
