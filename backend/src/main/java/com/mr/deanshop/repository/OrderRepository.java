package com.mr.deanshop.repository;

import com.mr.deanshop.auth.entity.User;
import com.mr.deanshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findOrderByUser(User user);
}
