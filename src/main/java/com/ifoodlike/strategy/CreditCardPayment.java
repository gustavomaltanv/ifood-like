package com.ifoodlike.strategy;

import com.ifoodlike.model.Order;

public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;

    public CreditCardPayment(String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public boolean processPayment(double amount, Order order) {
        if (amount > 0 && cardNumber != null && !cardNumber.isEmpty()) {
            return true;
        }
        return false;
    }
}