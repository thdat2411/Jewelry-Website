package com.example.library.service;

import com.example.library.dto.ProductDto;
import com.example.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<Product> findAll();
    List<ProductDto> allProduct();
    Product save(MultipartFile multipartFile,ProductDto productDto) throws IOException;
    Product update(MultipartFile imageProduct, ProductDto productDto);
    void deleteById(Long id);
    void enableById(Long id);
    List<ProductDto> products();
    ProductDto getById(Long id);
    Page<ProductDto> getallProduct(int pageNo);
    List<ProductDto> getProductByPrice(double init, double end);
    List<Long> getProductIdList(List<ProductDto>productDtoList);
    List<ProductDto> getOrderPriceProduct(List<Long> productIds,String flag);
    Page<ProductDto> searchProducts(int pageNo, String keyword);
    List<ProductDto> findProductByActivated(String keyword);
    List<ProductDto> findsampleProductForCustomer();
    List<ProductDto> findAllByCategory(String category);


}
