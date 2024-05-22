package com.example.library.repository;

import com.example.library.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("delete from Order p where p.orderId=?1")
    @Modifying
    @Transactional
    void DeleteByOrderID(Long id);

    @Query("select p from Order p")
    List<Order> findAll();
}
