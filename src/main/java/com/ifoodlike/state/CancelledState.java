package com.ifoodlike.state;

import com.ifoodlike.model.Order;

public class CancelledState implements OrderState {

    @Override
    public void nextState(Order order) {
        throw new UnsupportedOperationException("Don't have a next state, order has been cancelled.");
    }

    @Override
    public void cancelOrder(Order order) {
        throw new UnsupportedOperationException("Order is already cancelled.");
    }

    @Override
    public String getStatus() {
        return "Cancelado";
    }
}