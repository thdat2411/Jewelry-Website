package com.example.library.repository;

import com.example.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select p from Product p where p.productIsActivated=true and p.productIsDeleted=false")
    List<Product> getallProduct(Pageable pageable);
    @Query("select p from Product p where p.productName like %?1% or p.productType like %?1% order by p.productPrice asc")
    List<Product> findAllByNameOrDescription(String keyword);
    @Query("select p from Product p where  p.productIsActivated=true and p.productName like %?1% or p.productType like %?1% order by p.productPrice asc")
    List<Product> searchAllByNameOrDescription(String keyword);
    @Query("select p from Product p inner join Category c ON c.categoryId = p.category.categoryId" +
            " where p.category.categoryName= ?1 and p.productIsActivated = true and p.productIsDeleted = false")
    List<Product> findAllByCategory(String category);
    @Query("select p from Product p where p.productIsDeleted= false and p.productIsActivated= true order by p.productPrice asc")
    List<Product> getAllProduct();

    @Query("select p from Product p where p.productPrice<=?2 and p.productPrice>=?1 and p.productIsActivated=true and p.productIsDeleted= false order by p.productPrice asc")
    List<Product> getProductByPrice(double init, double end);
}
