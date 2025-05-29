package com.ifoodlike.state;

import com.ifoodlike.model.Order;

public class PreparingState implements OrderState {

    @Override
    public void nextState(Order order) {
        order.changeState(new ShippingState());
    }

    @Override
    public void cancelOrder(Order order) {
        order.changeState(new CancelledState());
    }

    @Override
    public String getStatus() {
        return "Preparando";
    }
}