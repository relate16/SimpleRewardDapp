package com.example.simpleRewardDapp.repository;

import com.example.simpleRewardDapp.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
