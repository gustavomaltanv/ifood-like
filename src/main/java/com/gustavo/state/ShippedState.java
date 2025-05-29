package com.gustavo.state;

import com.gustavo.Order;

public class ShippedState implements OrderState {
    private static ShippedState instance;

    private ShippedState() {}

    public static ShippedState getInstance() {
        if (instance == null) {
            instance = new ShippedState();
        }
        return instance;
    }

    @Override
    public void nextState(Order order) {
        order.setState(DeliveredState.getInstance());
        order.notifyObservers("Pedido entregue.");
    }

    @Override
    public String getStateName() {
        return "Enviado";
    }
}
