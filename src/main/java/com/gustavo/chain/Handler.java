package com.gustavo.chain;

import com.gustavo.Order;

public interface Handler {
    void setNext(Handler next);
    void handle(Order order);
}
