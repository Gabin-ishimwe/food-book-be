package com.backend.app.foodbook.order.repository;

import com.backend.app.foodbook.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
