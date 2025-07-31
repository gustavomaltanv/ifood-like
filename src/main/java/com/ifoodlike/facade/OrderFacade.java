package com.ifoodlike.facade;

import com.ifoodlike.command.*;
import com.ifoodlike.model.Customer;
import com.ifoodlike.model.Order;
import com.ifoodlike.model.Restaurant;

public class OrderFacade {
    private final OrderInvoker invoker = new OrderInvoker();

    public Order criarPedido(String orderId, Customer customer, Restaurant restaurant) {
        CreateOrderCommand command = new CreateOrderCommand(orderId, customer, restaurant);
        invoker.setCommand(command);
        invoker.executeCommand();
        return command.getCreatedOrder();
    }

    public void cancelarPedido(Order order) {
        CancelOrderCommand command = new CancelOrderCommand(order);
        invoker.setCommand(command);
        invoker.executeCommand();
    }

    public void avancarEstadoPedido(Order order) {
        NextOrderStateCommand command = new NextOrderStateCommand(order);
        invoker.setCommand(command);
        invoker.executeCommand();
    }
}

