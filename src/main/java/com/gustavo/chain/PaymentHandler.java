package com.gustavo.chain;

import com.gustavo.Order;

public class PaymentHandler implements Handler {
    private Handler next;

    @Override
    public void setNext(Handler next) {
        this.next = next;
    }

    @Override
    public void handle(Order order) {
        System.out.println("Etapa: Processando pagamento...");
        order.notifyObservers("Pagamento processado com sucesso.");

        if (next != null) {
            next.handle(order);
        }
    }
}
