package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    @Size(min =2,max=15,message="First name should have 2-15 characters")
    private String customerFirstName;
    @Size(min =2,max=15,message="First name should have 2-15 characters")
    private String customerLastName;
    private String customerUserName;
    @Size(min = 2, max = 15, message = "Password contains 2-10 characters")
    private String customerPassword;
    @Size(min = 10, max = 15, message = "Phone number contains 10-15 characters")
    private String customerPhoneNumber;
    private String customerAddress;
}
