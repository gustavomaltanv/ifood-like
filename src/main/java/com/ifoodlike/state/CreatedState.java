package com.ifoodlike.state;

import com.ifoodlike.model.Order;

public class CreatedState implements OrderState {

    @Override
    public void nextState(Order order) {
        order.changeState(new PaymentState());
    }

    @Override
    public void cancelOrder(Order order) {
        order.changeState(new CancelledState());
    }

    @Override
    public String getStatus() {
        return "Criado";
    }
}