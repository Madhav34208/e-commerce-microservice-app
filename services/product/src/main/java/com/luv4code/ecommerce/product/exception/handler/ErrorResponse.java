package com.luv4code.ecommerce.product.exception.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
