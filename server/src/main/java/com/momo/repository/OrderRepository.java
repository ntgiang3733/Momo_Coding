package com.momo.repository;

import com.momo.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query("FROM Orders o ORDER BY o.createdAt DESC")
    List<Orders> getLatestOrders(@Param("limit") int limit);
}
