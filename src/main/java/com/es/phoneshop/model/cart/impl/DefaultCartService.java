package com.es.phoneshop.model.cart.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.product.Product;

import java.util.List;

public class DefaultCartService implements CartService {
    private static DefaultCartService instance;
    private ProductDao productDao;

    private Cart cart = new Cart();

    public static synchronized DefaultCartService getInstance() {
        if (instance == null) {
            instance = new DefaultCartService();
        }
        return instance;
    }

    private DefaultCartService() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void add(Long productId, int quantity) {
        Product product = productDao.getProduct(productId);
        cart.getItems().add(new CartItem(product, quantity));
    }
}
