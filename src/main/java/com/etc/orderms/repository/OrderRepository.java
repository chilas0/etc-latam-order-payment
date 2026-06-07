package com.etc.orderms.repository;

import com.etc.orderms.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository responsible for persistence operations
 * related to orders.
 */
@Repository
public interface OrderRepository
    extends JpaRepository<Order, Long> {
}
