package com.ifoodlike.prototype;

import com.ifoodlike.model.Customer;
import com.ifoodlike.model.Order;
import com.ifoodlike.model.OrderItem;
import com.ifoodlike.model.Restaurant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrototypeTest {
    @Test
    void testCloneOrderId() {
        Customer customer = new Customer("c1", "Cliente 1", "cliente1@email.com");
        Restaurant restaurant = new Restaurant("r1", "Restaurante 1", "rest1@email.com", "rest1", "Rua 1");
        Order original = new Order("o1", customer, restaurant);
        Order clone = original.clone();
        assertEquals(original.getOrderId(), clone.getOrderId());
    }

    @Test
    void testCloneOrderCustomer() {
        Customer customer = new Customer("c2", "Cliente 2", "cliente2@email.com");
        Restaurant restaurant = new Restaurant("r2", "Restaurante 2", "rest2@email.com", "rest2", "Rua 2");
        Order original = new Order("o2", customer, restaurant);
        Order clone = original.clone();
        assertEquals(original.getCustomer(), clone.getCustomer());
    }

    @Test
    void testCloneOrderItemsSize() {
        Customer customer = new Customer("c3", "Cliente 3", "cliente3@email.com");
        Restaurant restaurant = new Restaurant("r3", "Restaurante 3", "rest3@email.com", "rest3", "Rua 3");
        Order original = new Order("o3", customer, restaurant);
        original.getOrderItems().add(new OrderItem("Pizza"));
        original.getOrderItems().add(new OrderItem("Hamburguer"));
        Order clone = original.clone();
        assertEquals(original.getOrderItems().size(), clone.getOrderItems().size());
    }

    @Test
    void testCloneOrderItemsIndependence() {
        Customer customer = new Customer("c4", "Cliente 4", "cliente4@email.com");
        Restaurant restaurant = new Restaurant("r4", "Restaurante 4", "rest4@email.com", "rest4", "Rua 4");
        Order original = new Order("o4", customer, restaurant);
        original.getOrderItems().add(new OrderItem("Coxinha"));
        Order clone = original.clone();
        clone.getOrderItems().add(new OrderItem("Pastel"));
        assertNotEquals(original.getOrderItems().size(), clone.getOrderItems().size());
    }

    @Test
    void testCloneOrderItemContent() {
        Customer customer = new Customer("c5", "Cliente 5", "cliente5@email.com");
        Restaurant restaurant = new Restaurant("r5", "Restaurante 5", "rest5@email.com", "rest5", "Rua 5");
        Order original = new Order("o5", customer, restaurant);
        original.getOrderItems().add(new OrderItem("Refrigerante"));
        Order clone = original.clone();
        assertEquals("Refrigerante", clone.getOrderItems().get(0).getNome());
    }
}
