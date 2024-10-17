package com.luv4code.ecommerce.customer.mapper;

import com.luv4code.ecommerce.customer.dto.request.CustomerRequest;
import com.luv4code.ecommerce.customer.dto.response.CustomerResponse;
import com.luv4code.ecommerce.customer.model.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public Customer toCustomer(CustomerRequest request) {
        if (request == null)
            return null;
        return Customer.builder()
                .id(request.id())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .address(request.address())
                .build();
    }

    public CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
