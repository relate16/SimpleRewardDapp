package com.example.simpleRewardDapp.repository;

import com.example.simpleRewardDapp.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
 * 엔티티 설계 다시 할 예정 :
 * CartItemRepository는 cascade.ALL 설정시 필요하지 않음
 * CartItem 같은 중간 연결 엔티티는 주체 엔티티, 즉 Cart에서 cascade.ALL과 같이 연결해놓으면
 * Cart로 조회하고 Cart로 저장시키면 되기 때문에 중간 엔티티 Repository,
 * 즉 CartItemRepository는필요가 없다.
 * */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("select ci from CartItem ci join ci.item i where i.name = :name")
    List<CartItem> findByItemName(@Param("name") String itemName);

    @Query("select ci from CartItem ci join ci.cart c where c.id = :id")
    List<CartItem> findCartItemsByCartId(@Param("id") Long cartId);


}
