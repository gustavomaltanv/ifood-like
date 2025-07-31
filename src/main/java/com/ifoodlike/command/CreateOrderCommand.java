package com.ifoodlike.command;

import com.ifoodlike.model.Order;
import com.ifoodlike.model.Customer;
import com.ifoodlike.model.Restaurant;

public class CreateOrderCommand implements Command {
    private String orderId;
    private Customer customer;
    private Restaurant restaurant;
    private Order createdOrder;

    public CreateOrderCommand(String orderId, Customer customer, Restaurant restaurant) {
        this.orderId = orderId;
        this.customer = customer;
        this.restaurant = restaurant;
    }

    @Override
    public void execute() {
        this.createdOrder = new Order(orderId, customer, restaurant);
    }

    public Order getCreatedOrder() {
        return createdOrder;
    }
}

