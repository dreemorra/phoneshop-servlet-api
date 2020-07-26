package com.es.phoneshop.services.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.services.CartService;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public class DefaultCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + ".cart";
    private static DefaultCartService instance;
    private ProductDao productDao;

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
    public synchronized Cart getCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
        if (cart == null) {
            request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart = new Cart());
        }
        return cart;
    }

    @Override
    public synchronized void add(Cart cart, Long productId, int quantity) throws OutOfStockException {
        Product product = productDao.getProduct(productId);
        CartItem productInStock = cart.getItem(productId);

        if(product.getStock() < quantity) {
            throw new OutOfStockException(product, quantity, product.getStock());
        }

        //sums quantity if item is already in cart; otherwise adds item to cart
        if (productInStock != null) {
            productInStock.setQuantity(productInStock.getQuantity() + quantity);
        } else {
            cart.getItems().add(new CartItem(product, quantity));
        }
        product.setStock(product.getStock() - quantity);
    }
}
