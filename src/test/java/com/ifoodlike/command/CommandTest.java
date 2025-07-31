package com.ifoodlike.command;

import com.ifoodlike.model.Customer;
import com.ifoodlike.model.Order;
import com.ifoodlike.model.Restaurant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandTest {
    @Test
    void testCreateOrderCommandCreatesOrder() {
        Customer customer = new Customer("c1", "Cliente 1", "c1@email.com");
        Restaurant restaurant = new Restaurant("r1", "Restaurante 1", "r1@email.com", "rest1", "Rua 1");
        CreateOrderCommand command = new CreateOrderCommand("o1", customer, restaurant);
        command.execute();
        Order order = command.getCreatedOrder();
        assertEquals("o1", order.getOrderId());
    }

    @Test
    void testCancelOrderCommandCancelsOrder() {
        Customer customer = new Customer("c2", "Cliente 2", "c2@email.com");
        Restaurant restaurant = new Restaurant("r2", "Restaurante 2", "r2@email.com", "rest2", "Rua 2");
        Order order = new Order("o2", customer, restaurant);
        CancelOrderCommand command = new CancelOrderCommand(order);
        command.execute();
        assertEquals("Cancelado", order.getCurrentState().getStatus());
    }

    @Test
    void testNextOrderStateCommandAdvancesState() {
        Customer customer = new Customer("c3", "Cliente 3", "c3@email.com");
        Restaurant restaurant = new Restaurant("r3", "Restaurante 3", "r3@email.com", "rest3", "Rua 3");
        Order order = new Order("o3", customer, restaurant);
        NextOrderStateCommand command = new NextOrderStateCommand(order);
        command.execute();
        assertEquals("Pagamento", order.getCurrentState().getStatus());
    }

    @Test
    void testOrderInvokerExecutesCommand() {
        Customer customer = new Customer("c4", "Cliente 4", "c4@email.com");
        Restaurant restaurant = new Restaurant("r4", "Restaurante 4", "r4@email.com", "rest4", "Rua 4");
        Order order = new Order("o4", customer, restaurant);
        CancelOrderCommand command = new CancelOrderCommand(order);
        OrderInvoker invoker = new OrderInvoker();
        invoker.setCommand(command);
        invoker.executeCommand();
        assertEquals("Cancelado", order.getCurrentState().getStatus());
    }
}