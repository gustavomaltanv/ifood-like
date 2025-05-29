package com.ifoodlike.strategy;

import com.ifoodlike.model.Order;

public class PaymentService {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean executePayment(double amount, Order order) {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment strategy not set. Please set a strategy before executing payment.");
        }
        return paymentStrategy.processPayment(amount, order);
    }
}