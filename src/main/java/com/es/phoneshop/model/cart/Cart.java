package com.es.phoneshop.model.cart;

import com.es.phoneshop.exceptions.ProductNotFoundException;

import java.util.*;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public CartItem getItem(long productId) {
        Optional<CartItem> item = items.stream()
                .filter(o -> o.getProduct().getId().equals(productId))
                .findAny();
        return item.orElse(null);
    }

    @Override
    public String toString() {
        return "Cart[" +  items + ']';
    }
}
