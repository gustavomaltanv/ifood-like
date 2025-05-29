package com.ifoodlike.observer;

import com.ifoodlike.model.Order;

public interface OrderObserver {
    void update(Order order);
}
