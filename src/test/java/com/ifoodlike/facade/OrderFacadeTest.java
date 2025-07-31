package com.ifoodlike.facade;

import com.ifoodlike.model.Customer;
import com.ifoodlike.model.Order;
import com.ifoodlike.model.Restaurant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderFacadeTest {
    @Test
    void testCriarPedido() {
        OrderFacade facade = new OrderFacade();
        Customer customer = new Customer("c1", "Cliente 1", "c1@email.com");
        Restaurant restaurant = new Restaurant("r1", "Restaurante 1", "r1@email.com", "rest1", "Rua 1");
        Order order = facade.criarPedido("o1", customer, restaurant);
        assertEquals("o1", order.getOrderId());
    }

    @Test
    void testCancelarPedido() {
        OrderFacade facade = new OrderFacade();
        Customer customer = new Customer("c2", "Cliente 2", "c2@email.com");
        Restaurant restaurant = new Restaurant("r2", "Restaurante 2", "r2@email.com", "rest2", "Rua 2");
        Order order = facade.criarPedido("o2", customer, restaurant);
        facade.cancelarPedido(order);
        assertEquals("Cancelado", order.getCurrentState().getStatus());
    }

    @Test
    void testAvancarEstadoPedido() {
        OrderFacade facade = new OrderFacade();
        Customer customer = new Customer("c3", "Cliente 3", "c3@email.com");
        Restaurant restaurant = new Restaurant("r3", "Restaurante 3", "r3@email.com", "rest3", "Rua 3");
        Order order = facade.criarPedido("o3", customer, restaurant);
        facade.avancarEstadoPedido(order);
        assertEquals("Pagamento", order.getCurrentState().getStatus());
    }
}