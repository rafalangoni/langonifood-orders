package com.langonifood.order.repository;

import com.langonifood.order.model.Order;
import com.langonifood.order.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Order o set o.status = :status where o = :order")
    void updateStatus(Status status, Order order);

    @Query(value = "SELECT o from Order o LEFT JOIN FETCH o.items where o.id = :id")
    Order byIdWithItems(Long id);
}
