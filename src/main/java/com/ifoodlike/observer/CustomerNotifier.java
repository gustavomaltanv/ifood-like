package com.ifoodlike.observer;

import com.ifoodlike.model.Customer;
import com.ifoodlike.model.Order;

public class CustomerNotifier implements OrderObserver {
    private Customer customer;

    public CustomerNotifier(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void update(Order order) {
        this.customer.receiveNotification(order);
    }

    public Customer getCustomer() {
        return customer;
    }
}