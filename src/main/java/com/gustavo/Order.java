package com.gustavo;

import com.gustavo.chain.Handler;
import com.gustavo.observer.IObserver;
import com.gustavo.observer.ISubject;
import com.gustavo.state.CreatedState;
import com.gustavo.state.OrderState;

import java.util.ArrayList;
import java.util.List;

public class Order implements ISubject {
    private final List<IObserver> observers = new ArrayList<>();
    private OrderState state = CreatedState.getInstance();
    private Handler handler;

    public void setHandlerChain(Handler handler) {
        this.handler = handler;
    }

    public void process() {
        if (handler != null) {
            handler.handle(this);
        }
    }

    public void nextStep() {
        state.nextState(this);
    }

    public void setState(OrderState state) {
        this.state = state;
        System.out.println("Estado atual: " + state.getStateName());
    }

    public OrderState getState() {
        return state;
    }

    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (IObserver observer : observers) {
            observer.update(message);
        }
    }
}
