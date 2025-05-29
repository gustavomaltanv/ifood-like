package com.gustavo.state;

import com.gustavo.Order;

public class DeliveredState implements OrderState {
    private static DeliveredState instance;

    private DeliveredState() {}

    public static DeliveredState getInstance() {
        if (instance == null) {
            instance = new DeliveredState();
        }
        return instance;
    }

    @Override
    public void nextState(Order order) {
        order.notifyObservers("Pedido já foi entregue. Nenhuma transição adicional.");
    }

    @Override
    public String getStateName() {
        return "Entregue";
    }
}
