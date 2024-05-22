package com.example.library.service.impl;

import com.example.library.dto.CustomerDto;
import com.example.library.model.Customer;
import com.example.library.model.ShoppingCart;
import com.example.library.repository.CustomerRepository;
import com.example.library.repository.ShoppingCartRepository;
import com.example.library.service.CustomerService;
import com.example.library.service.ShoppingCartSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Customer save(CustomerDto customerDto) {
        Customer customer = new Customer();
        ShoppingCart cart = customer.getCart();
        cart.setCustomer(customer);
        customer.setCart(cart);
        customer.setCustomerFirstName(customerDto.getCustomerFirstName());
        customer.setCustomerLastName(customerDto.getCustomerLastName());
        customer.setCustomerPassword(customerDto.getCustomerPassword());
        customer.setCustomerUserName(customerDto.getCustomerUserName());
        return customerRepository.save(customer);
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByCustomerUserName(username);
    }

    @Override
    public CustomerDto getCustomer(String username) {
        CustomerDto customerDto = new CustomerDto();
        Customer customer = customerRepository.findByCustomerUserName(username);
        customerDto.setCustomerFirstName(customer.getCustomerFirstName());
        customerDto.setCustomerLastName(customer.getCustomerLastName());
        customerDto.setCustomerUserName(customer.getCustomerUserName());
        customerDto.setCustomerPassword(customer.getCustomerPassword());
        customerDto.setCustomerAddress(customer.getCustomerAddress());
        customerDto.setCustomerPhoneNumber(customer.getCustomerPhoneNumber());
        return customerDto;
    }

    @Override
    public Customer update(CustomerDto customerDto) {
        Customer customer = customerRepository.findByCustomerUserName(customerDto.getCustomerUserName());
        customer.setCustomerFirstName(customerDto.getCustomerFirstName());
        customer.setCustomerLastName(customerDto.getCustomerLastName());
        customer.setCustomerAddress(customerDto.getCustomerAddress());
        customer.setCustomerPhoneNumber(customerDto.getCustomerPhoneNumber());
        return customerRepository.save(customer);
    }

    @Override
    public Customer changePass(CustomerDto customerDto) {
        Customer customer = customerRepository.findByCustomerUserName(customerDto.getCustomerUserName());
        customer.setCustomerPassword(customerDto.getCustomerPassword());
        return customerRepository.save(customer);
    }
}
