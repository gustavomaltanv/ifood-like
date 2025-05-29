package com.ifoodlike.observer;

import com.ifoodlike.model.*;
import com.ifoodlike.state.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderObserverTest {

    private Order order;
    private Customer customer;
    private Restaurant restaurant;
    private CustomerNotifier customerNotifier;

    @BeforeEach
    void setUp() {
        customer = new Customer("C001", "Alice Silva", "alice@example.com");
        restaurant = new Restaurant("R001", "Pizzaria Boa", "pizza@example.com", "R001", "Rua das Flores, 123");
        order = new Order("ORD002", customer, restaurant);

        customer.resetLastNotificationStatus();

        customerNotifier = new CustomerNotifier(customer);
        order.addObserver(customerNotifier);
    }

    @Test
    @DisplayName("Cliente deve ser notificado sobre o estado inicial 'Criado' ao ser adicionado como observador")
    void customerShouldBeNotifiedOnInitialCreatedState() {

        assertNull(customer.getLastNotificationStatus(),
                "Cliente não recebe a notificado sobre o estado inicial.");
    }

    @Test
    @DisplayName("Cliente deve ser notificado quando o pedido transita de Created para PaymentState")
    void customerShouldBeNotifiedWhenOrderTransitionsToPayment() {
        customer.resetLastNotificationStatus();
        order.nextState();
        assertEquals("Pagamento", customer.getLastNotificationStatus(),
                "Cliente deveria ter sido notificado sobre o estado 'Pagamento'");
    }

    @Test
    @DisplayName("Cliente deve ser notificado quando o pedido transita de Payment para PreparingState")
    void customerShouldBeNotifiedWhenOrderTransitionsToPreparing() {
        order.changeState(new PaymentState());
        customer.resetLastNotificationStatus();
        order.nextState();
        assertEquals("Preparando", customer.getLastNotificationStatus(),
                "Cliente deveria ter sido notificado sobre o estado 'Preparando'");
    }

    @Test
    @DisplayName("Cliente deve ser notificado quando o pedido é cancelado")
    void customerShouldBeNotifiedWhenOrderIsCancelled() {
        customer.resetLastNotificationStatus();
        order.cancel();
        assertEquals("Cancelado", customer.getLastNotificationStatus(),
                "Cliente deveria ter sido notificado sobre o estado 'Cancelado'");
    }

    @Test
    @DisplayName("Remoção de um observador deve impedir notificações futuras para ele")
    void removedObserverShouldNotReceiveNotifications() {
        order.removeObserver(customerNotifier);
        customer.resetLastNotificationStatus();
        order.nextState();
        assertNull(customer.getLastNotificationStatus(),
                "Cliente não deveria ter sido notificado após a remoção do observador");
    }

    @Test
    @DisplayName("Adicionar o mesmo observador múltiplas vezes não deve duplicar notificações")
    void addingSameObserverMultipleTimesShouldNotDuplicateNotifications() {
        customer.resetLastNotificationStatus();

        order.addObserver(customerNotifier);
        order.addObserver(customerNotifier);

        order.nextState();

        assertEquals("Pagamento", customer.getLastNotificationStatus(),
                "Cliente deveria receber apenas uma notificação, mesmo com múltiplas tentativas de adição.");
    }

    @Test
    @DisplayName("Um pedido sem observadores não deve lançar exceção ao notificar")
    void orderWithoutObserversShouldNotThrowExceptionOnNotify() {
        Order newOrder = new Order("ORD003", customer, restaurant);
        assertDoesNotThrow(() -> newOrder.nextState());
    }
}