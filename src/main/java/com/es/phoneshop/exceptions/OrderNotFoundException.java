
package com.es.phoneshop.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String id) {
            super(id);
        }
    }