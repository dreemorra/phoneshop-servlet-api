package com.es.phoneshop.web;

import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.services.CartService;
import com.es.phoneshop.services.OrderService;
import com.es.phoneshop.services.impl.DefaultCartService;
import com.es.phoneshop.services.impl.DefaultOrderService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CheckoutPageServlet extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = DefaultCartService.getInstance();
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        request.setAttribute("order", orderService.getOrder(cart));
        request.setAttribute("paymentMethods", orderService.getPaymentMethods());
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.getOrder(cart);

        Map<String, String> errors = new HashMap<>();

        setRequiredValue(request, "firstName", errors, order::setFirstName);
        setRequiredValue(request, "lastName", errors, order::setLastName);
        setRequiredValue(request, "phone", errors, order::setPhone);

        setRequiredValue(request, "deliveryAddress", errors, order::setDeliveryAddress);
        setDeliveryDate(request, errors, order);
        setPaymentMethod(request, errors, order);


        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            doGet(request, response);
        } else {
            orderService.placeOrder(order);
            cartService.clear(request);
            response.sendRedirect(String.format(request.getContextPath() + "/order/overview/%s" , order.getSecureId()));
        }
    }

    private void setDeliveryDate (HttpServletRequest request, Map<String, String> errors, Order order) {
        String parameter = "deliveryDate";
        String value = request.getParameter(parameter);
        if (value == null || value.isEmpty()) {
            errors.put(parameter, "Value is required");
        } else {
            try {
                LocalDate localDate = LocalDate.parse(value);
                order.setDeliveryDate(localDate);
            } catch (DateTimeParseException e) {
                errors.put(parameter, "Date format is not correct. Format example: 27-12-1999");
            }
        }
    }

    private void setRequiredValue(HttpServletRequest request, String parameter, Map<String, String> errors, Consumer<String> consumer) {
        String value = request.getParameter(parameter);
        if(value == null || value.isEmpty()) {
            errors.put(parameter, "Value is required");
        } else {
            consumer.accept(value);
        }
    }

    private void setPaymentMethod (HttpServletRequest request, Map<String, String> errors, Order order) {
        String parameter = "paymentMethod";
        String value = request.getParameter(parameter);
        if (value == null || value.isEmpty()) {
            errors.put(parameter, "Value is required");
        } else {
            PaymentMethod paymentMethod = PaymentMethod.valueOf(value);
            order.setPaymentMethod(paymentMethod);
        }
    }
}