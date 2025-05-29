package com.ifoodlike.observer;

import com.ifoodlike.model.Order;
import com.ifoodlike.model.Restaurant;

public class RestaurantNotifier implements OrderObserver {
    private Restaurant restaurant;

    public RestaurantNotifier(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void update(Order order) {
        // this.restaurant.updateOrderStatus(order, order.getCurrentState());
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}