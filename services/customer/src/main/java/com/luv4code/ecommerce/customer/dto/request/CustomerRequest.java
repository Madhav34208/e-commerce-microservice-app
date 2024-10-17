package com.luv4code.ecommerce.customer.dto.request;

import com.luv4code.ecommerce.customer.model.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,

        @NotNull(message = "Customer Firstname is required")
        String firstname,

        @NotNull(message = "Customer Lastname is required")
        String lastname,

        @NotNull(message = "Customer Email is required")
        @Email(message = "Customer email is not a valid email address")
        String email,
        Address address
) {
}
