package com.gustavo.chain;

import com.gustavo.Order;

public class ShippingHandler implements Handler {
    private Handler next;

    @Override
    public void setNext(Handler next) {
        this.next = next;
    }

    @Override
    public void handle(Order order) {
        System.out.println("Etapa: Enviando pedido...");
        order.notifyObservers("Pedido enviado para transporte.");

        if (next != null) {
            next.handle(order);
        }
    }
}
