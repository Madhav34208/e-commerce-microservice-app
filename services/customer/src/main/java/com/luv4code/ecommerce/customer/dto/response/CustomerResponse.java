package com.luv4code.ecommerce.customer.dto.response;

import com.luv4code.ecommerce.customer.model.Address;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email,
        Address address
) {
}
