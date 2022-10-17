package com.example.simpleRewardDapp.repository;

import com.example.simpleRewardDapp.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * 엔티티 설계 다시 할 예정 :
 * OrderItemRepository는 cascade.ALL 설정시 필요하지 않음
 * OrderItem 같은 중간 연결 엔티티는 주체 엔티티, 즉 Order에서 cascade.ALL과 같이 연결해놓으면
 * Order로 조회하고 Order로 저장시키면 되기 때문에 중간 엔티티 Repository,
 * 즉 OrderItemRepository는필요가 없다.
 * */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
