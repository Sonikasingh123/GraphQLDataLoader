package com.ebay.dataloader.exception;

import org.jetbrains.annotations.NotNull;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
