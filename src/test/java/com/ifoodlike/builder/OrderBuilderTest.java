package com.ifoodlike.builder;

import com.ifoodlike.model.Customer;
import com.ifoodlike.model.Order;
import com.ifoodlike.model.OrderItem;
import com.ifoodlike.model.Restaurant;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderBuilderTest {
    @Test
    void testBuildOrderWithBasicDataOrderId() {
        Customer customer = new Customer("c1", "Cliente 1", "cliente1@email.com");
        Restaurant restaurant = new Restaurant("r1", "Restaurante 1", "rest1@email.com", "rest1", "Rua 1");
        Order order = new OrderBuilder()
                .setOrderId("o1")
                .setCustomer(customer)
                .setRestaurant(restaurant)
                .build();
        assertEquals("o1", order.getOrderId());
    }

    @Test
    void testBuildOrderWithBasicDataCustomer() {
        Customer customer = new Customer("c1", "Cliente 1", "cliente1@email.com");
        Restaurant restaurant = new Restaurant("r1", "Restaurante 1", "rest1@email.com", "rest1", "Rua 1");
        Order order = new OrderBuilder()
                .setOrderId("o1")
                .setCustomer(customer)
                .setRestaurant(restaurant)
                .build();
        assertEquals(customer, order.getCustomer());
    }

    @Test
    void testBuildOrderWithBasicDataRestaurant() {
        Customer customer = new Customer("c1", "Cliente 1", "cliente1@email.com");
        Restaurant restaurant = new Restaurant("r1", "Restaurante 1", "rest1@email.com", "rest1", "Rua 1");
        Order order = new OrderBuilder()
                .setOrderId("o1")
                .setCustomer(customer)
                .setRestaurant(restaurant)
                .build();
        assertEquals(restaurant, order.getRestaurant());
    }

    @Test
    void testBuildOrderWithBasicDataItemsEmpty() {
        Customer customer = new Customer("c1", "Cliente 1", "cliente1@email.com");
        Restaurant restaurant = new Restaurant("r1", "Restaurante 1", "rest1@email.com", "rest1", "Rua 1");
        Order order = new OrderBuilder()
                .setOrderId("o1")
                .setCustomer(customer)
                .setRestaurant(restaurant)
                .build();
        assertTrue(order.getOrderItems().isEmpty());
    }

    @Test
    void testBuildOrderWithItemsSize() {
        Customer customer = new Customer("c2", "Cliente 2", "cliente2@email.com");
        Restaurant restaurant = new Restaurant("r2", "Restaurante 2", "rest2@email.com", "rest2", "Rua 2");
        OrderItem item1 = new OrderItem("item1");
        OrderItem item2 = new OrderItem("item2");
        Order order = new OrderBuilder()
                .setOrderId("o2")
                .setCustomer(customer)
                .setRestaurant(restaurant)
                .addItem(item1)
                .addItem(item2)
                .build();
        assertEquals(2, order.getOrderItems().size());
    }
}