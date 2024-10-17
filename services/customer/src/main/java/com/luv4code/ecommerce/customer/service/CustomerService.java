package com.luv4code.ecommerce.customer.service;

import com.luv4code.ecommerce.customer.dto.request.CustomerRequest;
import com.luv4code.ecommerce.customer.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {

    String createCustomer(CustomerRequest request);

    void updateCustomer(CustomerRequest request);

    List<CustomerResponse> findAllCustomers();

    Boolean existsById(String customerId);

    CustomerResponse findById(String customerId);

    void deleteCustomer(String customerId);
}
