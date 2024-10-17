package com.luv4code.ecommerce.customer.service;

import com.luv4code.ecommerce.customer.dto.request.CustomerRequest;
import com.luv4code.ecommerce.customer.dto.response.CustomerResponse;
import com.luv4code.ecommerce.customer.exception.CustomerNotFoundException;
import com.luv4code.ecommerce.customer.mapper.CustomerMapper;
import com.luv4code.ecommerce.customer.model.Customer;
import com.luv4code.ecommerce.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    @Override
    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll().stream()
                .map(mapper::fromCustomer)
                .toList();
    }

    @Override
    public Boolean existsById(String customerId) {
        return customerRepository.existsById(customerId);
    }

    @Override
    public CustomerResponse findById(String customerId) {
        return customerRepository.findById(customerId)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(format("Cannot update customer:: No customer found with the provided ID:: $s", customerId)));
    }

    @Override
    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public void updateCustomer(CustomerRequest request) {
        var customer = customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Cannot update customer:: No customer found with the provided ID:: $s", request.id())
                ));
        mergeCustomer(customer, request);
        customerRepository.save(customer);
    }

    @Override
    public String createCustomer(CustomerRequest request) {
        var customer = customerRepository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstname()))
            customer.setFirstname(request.firstname());
        if (StringUtils.isNotBlank(request.lastname()))
            customer.setLastname(request.lastname());
        if (StringUtils.isNotBlank(request.email()))
            customer.setEmail(request.email());
        if (request.address() != null)
            customer.setAddress(request.address());
    }
}
