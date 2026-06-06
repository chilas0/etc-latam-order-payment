package com.etc.orderms.repository;

import com.etc.orderms.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository
    extends JpaRepository<Order, Long> {
}
