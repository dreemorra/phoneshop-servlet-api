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

    @Override
    public String toString() {
        return "Cart[" +  items + ']';
    }
}
