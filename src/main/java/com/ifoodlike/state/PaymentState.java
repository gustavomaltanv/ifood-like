package com.ifoodlike.state;

import com.ifoodlike.model.Order;

public class PaymentState extends OrderState {

    @Override
    public void prepare(Order order) {
        order.changeState(new PreparingState());
    }

    @Override
    public void cancel(Order order) {
        order.changeState(new CancelledState());
    }

    @Override
    public void nextState(Order order) {
        this.prepare(order);
    }

    @Override
    public String getStatus() {
        return "Pagamento";
    }
}