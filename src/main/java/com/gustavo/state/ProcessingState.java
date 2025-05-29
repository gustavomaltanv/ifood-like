package com.gustavo.state;

import com.gustavo.Order;

public class ProcessingState implements OrderState {
    private static ProcessingState instance;

    private ProcessingState() {}

    public static ProcessingState getInstance() {
        if (instance == null) {
            instance = new ProcessingState();
        }
        return instance;
    }

    @Override
    public void nextState(Order order) {
        order.setState(ShippedState.getInstance());
        order.notifyObservers("Pedido enviado.");
    }

    @Override
    public String getStateName() {
        return "Processando";
    }
}
