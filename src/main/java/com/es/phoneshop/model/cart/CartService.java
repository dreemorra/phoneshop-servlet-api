package com.es.phoneshop.model.cart;

import com.es.phoneshop.exceptions.OutOfStockException;

public interface CartService {
    Cart getCart();
    void add(Long productId, int quantity) throws OutOfStockException;
}
