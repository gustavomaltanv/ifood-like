package com.gustavo.state;

import com.gustavo.Order;

public interface OrderState {
    void nextState(Order order);
    String getStateName();
}
