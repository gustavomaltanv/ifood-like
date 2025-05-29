package com.ifoodlike.singleton;

import com.ifoodlike.model.Order;

import java.util.HashMap;
import java.util.Map;

public class OrderService {
    private static volatile OrderService instance;
    private Map<String, Order> orders;
    private OrderService() {
        this.orders = new HashMap<>();
    }

    public static OrderService getInstance() {
        if (instance == null) {
            synchronized (OrderService.class) {
                if (instance == null) {
                    instance = new OrderService();
                }
            }
        }
        return instance;
    }

    public void placeOrder(Order order) {
        if (orders.containsKey(order.getOrderId())) {
            throw new IllegalArgumentException("Order with ID " + order.getOrderId() + " already exists.");
        }
        orders.put(order.getOrderId(), order);
    }

    public Order getOrderById(String orderId) {
        return orders.get(orderId);
    }

    public boolean removeOrder(String orderId) {
        return orders.remove(orderId) != null;
    }

    public int getOrderCount() {
        return orders.size();
    }

    public void clearOrders() {
        orders.clear();
    }
}