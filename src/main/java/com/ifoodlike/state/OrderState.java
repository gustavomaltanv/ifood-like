package com.ifoodlike.state;

import com.ifoodlike.model.Order;

public abstract class OrderState {
    public void create(Order order) {
        throw new UnsupportedOperationException("Not possible to create order.");
    }
    public void pay(Order order) {
        throw new UnsupportedOperationException("Not possible to pay order.");
    }
    public void prepare(Order order) {
        throw new UnsupportedOperationException("Not possible to create order.");
    }
    public void ship(Order order) {
        throw new UnsupportedOperationException("Not possible to ship order.");
    }
    public void deliver(Order order) {
        throw new UnsupportedOperationException("Not possible to deliver order.");
    }
    public void cancel(Order order) {
        throw new UnsupportedOperationException("Not possible to cancel order.");
    }
    public void nextState(Order order) {
        throw new UnsupportedOperationException("Not possible to do anything with order.");
    }
    public abstract String getStatus();
}