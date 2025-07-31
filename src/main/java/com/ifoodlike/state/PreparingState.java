package com.ifoodlike.state;

import com.ifoodlike.model.Order;

public class PreparingState extends OrderState {

    @Override
    public void ship(Order order) {
        order.changeState(new ShippingState());
    }

    @Override
    public void cancel(Order order) {
        order.changeState(new CancelledState());
    }

    @Override
    public void nextState(Order order) {
        this.ship(order);
    }

    @Override
    public String getStatus() {
        return "Preparando";
    }
}