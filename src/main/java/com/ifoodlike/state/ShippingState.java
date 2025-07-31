package com.ifoodlike.state;

import com.ifoodlike.model.Order;

public class ShippingState extends OrderState {

    @Override
    public void deliver(Order order) {
        order.changeState(new DeliveredState());
    }

    @Override
    public void cancel(Order order) {
        order.changeState(new CancelledState());
    }

    public void nextState(Order order) {
        this.deliver(order);
    }

    @Override
    public String getStatus() {
        return "Em Trânsito";
    }
}