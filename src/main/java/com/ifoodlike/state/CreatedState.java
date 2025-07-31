package com.ifoodlike.state;

import com.ifoodlike.model.Order;

public class CreatedState extends OrderState {

    @Override
    public void pay(Order order) {
        order.changeState(new PaymentState());
    }

    @Override
    public void cancel(Order order) {
        order.changeState(new CancelledState());
    }

    @Override
    public String getStatus() {
        return "Criado";
    }

    @Override
    public void nextState(Order order) {
        this.pay(order);
    }
}