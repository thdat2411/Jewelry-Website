package com.example.library.service.impl;

import com.example.library.dto.ProductDto;
import com.example.library.model.CartItem;
import com.example.library.model.Customer;
import com.example.library.model.Product;
import com.example.library.model.ShoppingCart;
import com.example.library.repository.CartItemRepository;
import com.example.library.repository.ProductRepository;
import com.example.library.repository.ShoppingCartRepository;
import com.example.library.service.CustomerService;
import com.example.library.service.ProductService;
import com.example.library.service.ShoppingCartSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
@Service
public class ShoppingCartImpl implements ShoppingCartSevice {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public ShoppingCart addItemToCart(ProductDto productDto, int quantity, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getCart();

        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            shoppingCart.setCustomer(customer);
            shoppingCart = shoppingCartRepository.save(shoppingCart);
        }
        Set<CartItem> cartItemList = shoppingCart.getCartItems();
        CartItem cartItem = find(cartItemList, productDto.getProductId());
        Product product = productRepository.getReferenceById(productDto.getProductId());

        double unitPrice = productDto.getProductPrice();

        int itemQuantity = 0;
        if (cartItemList == null) {
            cartItemList = new HashSet<>();
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setShoppingCart(shoppingCart);
                cartItem.setCartItemQuantity(quantity);
                cartItem.setCartItemUnitPrice(unitPrice);
                cartItemList.add(cartItem);
                cartItemRepository.save(cartItem);
            } else {
                itemQuantity = cartItem.getCartItemQuantity() + quantity;
                cartItem.setCartItemQuantity(itemQuantity);
                cartItemRepository.save(cartItem);
            }
        } else {
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setShoppingCart(shoppingCart);
                cartItem.setCartItemQuantity(quantity);
                cartItem.setCartItemUnitPrice(unitPrice);
                cartItemList.add(cartItem);
                cartItemRepository.save(cartItem);
            } else {
                itemQuantity = cartItem.getCartItemQuantity() + quantity;
                cartItem.setCartItemQuantity(itemQuantity);
                cartItemRepository.save(cartItem);
            }
        }
        product.setProductQuantity(product.getProductQuantity()-quantity);
        shoppingCart.setCartItems(cartItemList);
        double totalPrice = totalPrice(shoppingCart.getCartItems());
        int totalItem = totalItem(shoppingCart.getCartItems());
        shoppingCart.setCartTotalPrice(totalPrice);
        shoppingCart.setCartTotalItems(totalItem);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart updateCart(ProductDto productDto, int quantity, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getCart();
        Set<CartItem> cartItemList = shoppingCart.getCartItems();
        CartItem item = find(cartItemList, productDto.getProductId());
        item.getProduct().setProductQuantity(item.getProduct().getProductQuantity() + item.getCartItemQuantity());
        if(quantity==0)
        {
            cartItemList.remove(item);
            cartItemRepository.delete(item);
            double totalPrice = totalPrice(cartItemList);
            int totalItem = totalItem(cartItemList);
            shoppingCart.setCartItems(cartItemList);
            shoppingCart.setCartTotalPrice(totalPrice);
            shoppingCart.setCartTotalItems(totalItem);
        }
        else {
            int itemQuantity = quantity;
            item.setCartItemQuantity(itemQuantity);
            item.getProduct().setProductQuantity(item.getProduct().getProductQuantity() - itemQuantity);
            cartItemRepository.save(item);
            shoppingCart.setCartItems(cartItemList);
            int totalItem = totalItem(cartItemList);
            double totalPrice = totalPrice(cartItemList);
            shoppingCart.setCartTotalPrice(totalPrice);
            shoppingCart.setCartTotalItems(totalItem);
        }
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart removeItemFromCart(ProductDto productDto, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getCart();
        Set<CartItem> cartItemList = shoppingCart.getCartItems();
        CartItem item = find(cartItemList, productDto.getProductId());
        item.getProduct().setProductQuantity(item.getProduct().getProductQuantity() + item.getCartItemQuantity());
        cartItemList.remove(item);
        cartItemRepository.delete(item);
        double totalPrice = totalPrice(cartItemList);
        int totalItem = totalItem(cartItemList);
        shoppingCart.setCartItems(cartItemList);
        shoppingCart.setCartTotalPrice(totalPrice);
        shoppingCart.setCartTotalItems(totalItem);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public void deleteCartById(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.getCartById(id);
        if(shoppingCart.getCartTotalItems()!=0) {
            for (CartItem cartItem : shoppingCart.getCartItems())
            {
                cartItemRepository.DeleteByCartId(cartItem.getCartItemId());
            }
        }
        shoppingCart.getCartItems().clear();
        shoppingCart.setCartTotalPrice(0);
        shoppingCart.setCartTotalItems(0);
        shoppingCartRepository.save(shoppingCart);
    }

    private CartItem find(Set<CartItem> cartItems, long productId) {
        if (cartItems == null) {
            return null;
        }
        CartItem cartItem = null;
        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId()== productId) {
                cartItem = item;
            }
        }
        return cartItem;
    }
    private Product transfer(ProductDto productDto) {
        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setProductName(productDto.getProductName());
        product.setProductQuantity(productDto.getProductQuantity());
        product.setProductPrice(productDto.getProductPrice());
        product.setProductDesc(productDto.getProductDesc());
        product.setProductImage(productDto.getProductImage());
        product.setProductIsActivated(productDto.isProductActivated());
        product.setProductIsDeleted(productDto.isProductDeleted());
        product.setCategory(productDto.getCategory());
        return product;
    }
    private double totalPrice(Set<CartItem> cartItemList) {
        double totalPrice = 0.0;
        for (CartItem item : cartItemList) {
            totalPrice += item.getCartItemUnitPrice() * item.getCartItemQuantity();
        }
        return totalPrice;
    }
    private int totalItem(Set<CartItem> cartItemList) {
        int totalItem = 0;
        for (CartItem item : cartItemList) {
            totalItem += item.getCartItemQuantity();
        }
        return totalItem;
    }
}
