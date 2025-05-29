package com.gustavo.state;

import com.gustavo.Order;

public class CreatedState implements OrderState {
    private static CreatedState instance;

    private CreatedState() {}

    public static CreatedState getInstance() {
        if (instance == null) {
            instance = new CreatedState();
        }
        return instance;
    }

    @Override
    public void nextState(Order order) {
        order.setState(ProcessingState.getInstance());
        order.notifyObservers("Pedido em processamento.");
    }

    @Override
    public String getStateName() {
        return "Criado";
    }
}
