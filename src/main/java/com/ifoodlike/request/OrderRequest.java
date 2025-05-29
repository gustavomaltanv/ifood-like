package com.ifoodlike.request;

import com.ifoodlike.model.Order;
import com.ifoodlike.model.User;

/**
 * Representa uma requisição para ser processada pela cadeia de responsabilidade.
 */
public class OrderRequest {
    private Order order;
    private User user;
    private String action; // "PLACE_ORDER", "MAKE_PAYMENT", "PREPARE_ORDER", "SHIP_ORDER", "DELIVER_ORDER", "CANCEL_ORDER"
    private double amount;

    public OrderRequest(Order order, User user, String action) {
        this.order = order;
        this.user = user;
        this.action = action;
        this.amount = 0.0;
    }

    public OrderRequest(Order order, User user, String action, double amount) {
        this.order = order;
        this.user = user;
        this.action = action;
        this.amount = amount;
    }

    public Order getOrder() {
        return order;
    }

    public User getUser() {
        return user;
    }

    public String getAction() {
        return action;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "orderId='" + order.getOrderId() + '\'' +
                ", user=" + user.getName() +
                ", action='" + action + '\'' +
                (amount > 0 ? ", amount=" + amount : "") +
                '}';
    }
}