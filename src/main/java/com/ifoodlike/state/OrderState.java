package com.ifoodlike.state;

import com.ifoodlike.model.Order;

public interface OrderState {
    void nextState(Order order);
    void cancelOrder(Order order);
    String getStatus();
}