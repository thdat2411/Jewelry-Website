package com.example.library.repository;

import com.example.library.model.Customer;
import com.example.library.model.ShoppingCart;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("select p from ShoppingCart p where p.cartId=?1")
    ShoppingCart getCartById(Long id);
}
