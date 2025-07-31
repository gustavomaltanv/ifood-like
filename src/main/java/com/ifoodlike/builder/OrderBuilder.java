package com.ifoodlike.builder;

import com.ifoodlike.model.Customer;
import com.ifoodlike.model.Order;
import com.ifoodlike.model.OrderItem;
import com.ifoodlike.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
    private String orderId;
    private Customer customer;
    private Restaurant restaurant;
    private List<OrderItem> orderItems = new ArrayList<>();

    public OrderBuilder setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderBuilder setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public OrderBuilder setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public OrderBuilder addItem(OrderItem item) {
        this.orderItems.add(item);
        return this;
    }

    public Order build() {
        Order order = new Order(orderId, customer, restaurant);
        order.setOrderItems(orderItems);
        return order;
    }
}
