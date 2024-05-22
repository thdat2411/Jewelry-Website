package com.example.library.service.impl;

import com.example.library.dto.ProductDto;
import com.example.library.model.Product;
import com.example.library.repository.ProductRepository;
import com.example.library.service.ProductService;
import com.example.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageUpload imageUpload;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductDto> allProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = transferData(products);
        return productDtos;
    }

    private List<ProductDto> transferData(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setProductId(product.getProductId());
            productDto.setProductName(product.getProductName());
            productDto.setProductQuantity(product.getProductQuantity());
            productDto.setProductPrice(product.getProductPrice());
            productDto.setProductDesc(product.getProductDesc());
            productDto.setProductImage(product.getProductImage());
            productDto.setCategory(product.getCategory());
            productDto.setProductType(product.getProductType());
            productDto.setProductActivated(product.isProductIsActivated());
            productDto.setProductDeleted(product.isProductIsDeleted());
            productDtos.add(productDto);
        }
        return productDtos;
    }



    @Override
    public Product save(MultipartFile multipartFile,ProductDto productDto) throws IOException {
        Product product = new Product();
        try {
            if (multipartFile == null) {
                product.setProductImage(null);
            } else {
                imageUpload.uploadImage(multipartFile);
                product.setProductImage(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
            }
            product.setProductName(productDto.getProductName());
            product.setProductDesc(productDto.getProductDesc());
            product.setProductPrice(productDto.getProductPrice());
            product.setCategory(productDto.getCategory());
            product.setProductType(productDto.getProductType());
            product.setProductIsDeleted(false);
            product.setProductIsActivated(true);
            product.setProductQuantity(productDto.getProductQuantity());
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product productUpdate = productRepository.getReferenceById(productDto.getProductId());
            if (imageProduct.getBytes().length > 0) {
                if (imageUpload.checkExist(imageProduct)) {
                    productUpdate.setProductImage(productUpdate.getProductImage());
                } else {
                    imageUpload.uploadImage(imageProduct);
                    productUpdate.setProductImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
                }
            }
            productUpdate.setCategory(productDto.getCategory());
            productUpdate.setProductId(productUpdate.getProductId());
            productUpdate.setProductName(productDto.getProductName());
            productUpdate.setProductQuantity(productDto.getProductQuantity());
            productUpdate.setProductPrice(productDto.getProductPrice());
            productUpdate.setProductDesc(productDto.getProductDesc());
            productUpdate.setProductType(productDto.getProductType());
            return productRepository.save(productUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.getById(id);
        product.setProductIsDeleted(true);
        product.setProductIsActivated(false);
        productRepository.save(product);
    }

    @Override
    public void enableById(Long id) {
        Product product = productRepository.getById(id);
        product.setProductIsActivated(true);
        product.setProductIsDeleted(false);
        productRepository.save(product);
    }

    @Override
    public List<ProductDto> products() {
        return transferData(productRepository.getAllProduct());
    }

    @Override
    public ProductDto getById(Long id) {
        ProductDto productDto = new ProductDto();
        Product product = productRepository.getById(id);
        productDto.setProductId(product.getProductId());
        productDto.setProductName(product.getProductName());
        productDto.setProductDesc(product.getProductDesc());
        productDto.setProductPrice(product.getProductPrice());
        productDto.setProductQuantity(product.getProductQuantity());
        productDto.setCategory(product.getCategory());
        productDto.setProductType(product.getProductType());
        productDto.setProductImage(product.getProductImage());
        return productDto;
    }

    @Override
    public Page<ProductDto> getallProduct(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 6);
        List<ProductDto> productDtoLists = this.allProduct();
        Page<ProductDto> productDtoPage = toPage(productDtoLists, pageable);
        return productDtoPage;
    }

    @Override
    public List<ProductDto> getProductByPrice(double init, double end) {
        List<Product> products = productRepository.getProductByPrice(init,end);
        List<ProductDto> productDtoList = transferData(products);
        return productDtoList;
    }

    @Override
    public List<Long> getProductIdList(List<ProductDto> productDtoList) {
        List<Long> productIdList = new ArrayList<>();
        for (ProductDto productDto : productDtoList)
        {
            productIdList.add(productDto.getProductId());
        }
        return productIdList;
    }

    @Override
    public List<ProductDto> getOrderPriceProduct(List<Long> productIds, String flag) {
        List<ProductDto> productDtoList = new ArrayList<>();
        while(!productIds.isEmpty()) {
            double max = 0;
            double min = 999999;
            ProductDto product = new ProductDto();
            if(Objects.equals(flag, "DESC")) {
                for (Long item : productIds) {
                    ProductDto productDto = getById(item);
                    if (productDto.getProductPrice() > max) {
                        max = productDto.getProductPrice();
                        product = productDto;
                    }
                }
            }
            if(Objects.equals(flag, "ASC"))
            {
                for (Long item : productIds) {
                    ProductDto productDto = getById(item);
                    if (productDto.getProductPrice() < min) {
                        min = productDto.getProductPrice();
                        product = productDto;
                    }
                }
            }
            productDtoList.add(product);
            productIds.remove(product.getProductId());
        }
        return productDtoList;
    }

    @Override
    public Page<ProductDto> searchProducts(int pageNo, String keyword) {
        List<Product> products = productRepository.findAllByNameOrDescription(keyword);
        List<ProductDto> productDtoList = transferData(products);
        Pageable pageable = PageRequest.of(pageNo, 6);
        Page<ProductDto> dtoPage = toPage(productDtoList, pageable);
        return dtoPage;
    }

    @Override
    public List<ProductDto> findProductByActivated(String keyword) {
        List<Product> products = productRepository.searchAllByNameOrDescription(keyword);
        List<ProductDto> productDtoList = transferData(products);
        return productDtoList;
    }

    @Override
    public List<ProductDto> findsampleProductForCustomer() {
        Pageable pageable = PageRequest.of(0,8);
        List<Product> products = productRepository.getallProduct(pageable);
        List<ProductDto> productDtos = transferData(products);
        return productDtos;
    }

    @Override
    public List<ProductDto> findAllByCategory(String category) {
        return transferData(productRepository.findAllByCategory(category));
    }

    private Page toPage(List list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size()
                : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

}
