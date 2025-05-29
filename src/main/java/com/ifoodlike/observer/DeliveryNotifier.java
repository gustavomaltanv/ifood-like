package com.ifoodlike.observer;

import com.ifoodlike.model.DeliveryDriver;
import com.ifoodlike.model.Order;

public class DeliveryNotifier implements OrderObserver {
    private DeliveryDriver deliveryDriver;

    public DeliveryNotifier(DeliveryDriver deliveryDriver) {
        this.deliveryDriver = deliveryDriver;
    }

    @Override
    public void update(Order order) {

    }

    public DeliveryDriver getDeliveryDriver() {
        return deliveryDriver;
    }
}