package com.ifoodlike.model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private List<Order> orderHistory;
    private String lastNotificationStatus;

    public Customer(String userId, String name, String email) {
        super(userId, name, email);
        this.orderHistory = new ArrayList<>();
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void addOrderToHistory(Order order) {
        this.orderHistory.add(order);
    }

    public void receiveNotification(Order order) {
        this.lastNotificationStatus = order.getCurrentState().getStatus();
    }

    public String getLastNotificationStatus() {
        return lastNotificationStatus;
    }

    public void resetLastNotificationStatus() {
        this.lastNotificationStatus = null;
    }
}