package com.es.phoneshop.web;

import com.es.phoneshop.services.CartService;
import com.es.phoneshop.services.impl.DefaultCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MiniCartServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("totalQuantity", cartService.getCart(request).getTotalQuantity());
        request.getRequestDispatcher("/WEB-INF/pages/minicart.jsp").include(request, response);
    }
}