package com.ifoodlike.model;

import com.ifoodlike.state.OrderState;

public class Restaurant extends User {
    private String restaurantId;
    private String address;

    public Restaurant(String userId, String name, String email, String restaurantId, String address) {
        super(userId, name, email);
        this.restaurantId = restaurantId;
        this.address = address;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getAddress() {
        return address;
    }

    public void updateOrderStatus(Order order, OrderState newState) {
        order.changeState(newState);
    }
}