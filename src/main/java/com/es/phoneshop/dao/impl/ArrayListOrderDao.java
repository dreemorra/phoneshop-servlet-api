package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ArrayListOrderDao implements OrderDao {
    private static OrderDao instance;
    private long orderId;
    private List<Order> orderList;

    public static synchronized OrderDao getInstance() {
        if (instance == null) {
            instance = new ArrayListOrderDao();
        }
        return instance;
    }

    private ArrayListOrderDao() {
        this.orderList = new ArrayList<>();
    }


    @Override
    public synchronized Order getOrder(Long id) throws OrderNotFoundException {
        return orderList.stream()
                .filter(order -> id.equals(order.getId()))
                .findAny()
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public synchronized void save(Order order) throws OrderNotFoundException {
        if (order.getId() != null) {
            Optional<Order> optionalOrder = orderList.stream()
                    .filter(o -> o.getId().equals(order.getId()))
                    .findAny();
            if (optionalOrder.isPresent()) {
                Order updatedOrder = optionalOrder.get();
                updateOrder(updatedOrder, order);
            } else {
                throw new NoSuchElementException("Unable to find order with given id");
            }
        }
        else {
            order.setId(orderId++);
            orderList.add(order);
        }
    }

    private void updateOrder(Order oldOrder, Order newOrder) {
        newOrder.setSubtotal(oldOrder.getSubtotal());
        newOrder.setDeliveryCost(oldOrder.getDeliveryCost());
        newOrder.setFirstName(oldOrder.getFirstName());
        newOrder.setLastName(oldOrder.getLastName());
        newOrder.setPhone(oldOrder.getPhone());
        newOrder.setDeliveryDate(oldOrder.getDeliveryDate());
        newOrder.setDeliveryAddress(oldOrder.getDeliveryAddress());
        newOrder.setPaymentMethod(oldOrder.getPaymentMethod());
    }
}