package com.example.simpleRewardDapp.repository;

import com.example.simpleRewardDapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
