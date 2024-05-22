package com.example.library.repository;

import com.example.library.model.CartItem;
import org.hibernate.sql.Delete;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    @Modifying
    @Query("delete from CartItem c where c.cartItemId=?1")
    void DeleteByCartId(Long id);
}
