package com.ifoodlike.strategy;

import com.ifoodlike.model.Order;

public class PixPayment implements PaymentStrategy {
    private String pixKey;

    public PixPayment(String pixKey) {
        this.pixKey = pixKey;
    }

    @Override
    public boolean processPayment(double amount, Order order) {
        if (amount > 0 && pixKey != null && !pixKey.isEmpty()) {
            return true;
        }
        return false;
    }
}