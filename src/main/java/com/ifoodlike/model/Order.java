package com.ifoodlike.model;

import com.ifoodlike.state.CancelledState;
import com.ifoodlike.state.CreatedState;
import com.ifoodlike.state.OrderState;
import com.ifoodlike.observer.OrderObserver;
import com.ifoodlike.state.PaymentState;
import com.ifoodlike.strategy.PaymentService;
import com.ifoodlike.strategy.PaymentStrategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Order implements Cloneable, Iterable<OrderItem> {
    private String orderId;
    private Customer customer;
    private Restaurant restaurant;
    private OrderState currentState;
    private List<OrderItem> orderItems = new ArrayList<>();
    private List<OrderObserver> observers;

    public Order(String orderId, Customer customer, Restaurant restaurant) {
        this.orderId = orderId;
        this.customer = customer;
        this.restaurant = restaurant;
        this.currentState = new CreatedState();
        this.observers = new ArrayList<>();
    }

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public OrderState getCurrentState() {
        return currentState;
    }

    public void changeState(OrderState newState) {
        this.currentState = newState;
        notifyObservers();
    }

    public void nextState() {
        this.currentState.nextState(this);
    }

    public void cancel() {
        this.currentState.cancel(this);
    }

    public boolean initiatePayment(double amount, PaymentStrategy paymentStrategy) {
        if (!(currentState instanceof CreatedState)) {
            return false;
        }

        PaymentService paymentService = new PaymentService();
        paymentService.setPaymentStrategy(paymentStrategy);

        boolean paymentSuccess = false;
        try {
            paymentSuccess = paymentService.executePayment(amount, this);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        if (paymentSuccess) {
            changeState(new PaymentState());
            return true;
        } else {
            changeState(new CancelledState());
            return false;
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customer=" + (customer != null ? customer.getName() : "N/A") +
                ", restaurant=" + (restaurant != null ? restaurant.getName() : "N/A") +
                ", currentState=" + currentState.getStatus() +
                '}';
    }

    public void addObserver(OrderObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(OrderObserver observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.update(this);
        }
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public Order clone() {
        try {
            Order cloned = (Order) super.clone();
            cloned.orderItems = new ArrayList<>();
            Iterator<OrderItem> it = this.orderItems.iterator();
            while (it.hasNext()) {
                OrderItem item = it.next();
                OrderItem clonedItem = new OrderItem(item.getNome());
                cloned.orderItems.add(clonedItem);
            }
            cloned.observers = new ArrayList<>();
            return cloned;
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public Iterator<OrderItem> iterator() {
        return orderItems.iterator();
    }
}