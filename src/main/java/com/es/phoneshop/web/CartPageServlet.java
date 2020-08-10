package com.es.phoneshop.web;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.services.CartService;
import com.es.phoneshop.services.impl.DefaultCartService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        Map<Long, String> errors = new HashMap<>();

        for (int i = 0; i < productIds.length; i++) {
            Long productId = Long.valueOf(productIds[i]);

            Cart cart = cartService.getCart(request);
            try {
                cartService.update(cart, productId, Integer.parseInt(quantities[i]));
            } catch (NumberFormatException | OutOfStockException e) {
                if (e.getClass().equals(NumberFormatException.class)) {
                    errors.put(productId, "It's not a number");
                } else if (e.getClass().equals(OutOfStockException.class)) {
                    errors.put(productId, "Out of stock, available " + ((OutOfStockException) e).getStockAvailable());
                }
            }
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            doGet(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/cart"
                    + "?message=Cart updated successfully!");
        }
    }
}