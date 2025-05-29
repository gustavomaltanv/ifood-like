package com.ifoodlike.state;

import com.ifoodlike.model.Order;

public class DeliveredState implements OrderState {

    @Override
    public void nextState(Order order) {
        throw new UnsupportedOperationException("Order already finished.");
    }

    @Override
    public void cancelOrder(Order order) {
        throw new UnsupportedOperationException("Not possible to cancel a finished order.");
    }

    @Override
    public String getStatus() {
        return "Entregue";
    }
}