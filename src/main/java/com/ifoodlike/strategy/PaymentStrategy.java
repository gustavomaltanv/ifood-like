package com.ifoodlike.strategy;

import com.ifoodlike.model.Order;

public interface PaymentStrategy {
    boolean processPayment(double amount, Order order);
}