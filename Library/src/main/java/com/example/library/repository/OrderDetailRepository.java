package com.example.library.repository;

import com.example.library.model.Order;
import com.example.library.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    @Query("delete from OrderDetail o where o.oderdetailId=?1")
    @Modifying
    @Transactional
    void DeleteByOrderDetailID(Long id);

    @Query("select o from OrderDetail o where o.order.orderId=?1")
    List<OrderDetail> getOrders(Long id);
}
