package com.gustavo.chain;

import com.gustavo.Order;

public class PackingHandler implements Handler {
    private Handler next;

    @Override
    public void setNext(Handler next) {
        this.next = next;
    }

    @Override
    public void handle(Order order) {
        System.out.println("Etapa: Empacotando pedido...");
        order.notifyObservers("Pedido empacotado.");

        if (next != null) {
            next.handle(order);
        }
    }
}
