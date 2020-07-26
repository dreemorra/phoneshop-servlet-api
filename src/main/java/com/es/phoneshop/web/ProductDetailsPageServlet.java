package com.es.phoneshop.web;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.services.CartService;
import com.es.phoneshop.services.RecentlyViewedProductsService;
import com.es.phoneshop.services.impl.DefaultCartService;
import com.es.phoneshop.services.impl.DefaultRecentlyViewedProductsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class ProductDetailsPageServlet  extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private RecentlyViewedProductsService recentlyViewedService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
        recentlyViewedService = DefaultRecentlyViewedProductsService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = productDao.getProduct(parseProductId(request));
        recentlyViewedService.addProduct(request, product);
        request.setAttribute("product", product);
        request.setAttribute("cart", cartService.getCart(request));
        request.setAttribute("recentlyViewedProducts", recentlyViewedService.getList(request));
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = parseProductId(request);
        String quantityString = request.getParameter("quantity");
        int quantity = 0;
        try {
            NumberFormat format = NumberFormat.getInstance(request.getLocale());
            quantity = format.parse(quantityString).intValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Cart cart = cartService.getCart(request);
        try {
            cartService.add(cart, productId, quantity);
        } catch (OutOfStockException e) {
            request.setAttribute("error", "Out of stock, available " + e.getStockAvailable());
            doGet(request, response);
            return;
        }
        request.setAttribute("message", "Product added to cart");

        response.sendRedirect(request.getContextPath() + "/products/" + productId + "?message=Product added to cart");
    }

    private Long parseProductId(HttpServletRequest request) {
        String productId = request.getPathInfo().substring(1);
        return Long.valueOf(productId);
    }
}
