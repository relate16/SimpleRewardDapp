package com.example.simpleRewardDapp.repository;

import com.example.simpleRewardDapp.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("select ci from CartItem ci join ci.item i where i.name = :name")
    List<CartItem> findByItemName(@Param("name") String itemName);

    @Query("select ci from CartItem ci join ci.cart c where c.id = :id")
    List<CartItem> findCartItemsByCartId(@Param("id") Long cartId);


}
