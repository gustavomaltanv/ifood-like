package com.ifoodlike.state;

import com.ifoodlike.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderStateTest {

    private Order order;
    private Customer customer;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        customer = new Customer("C001", "Alice Silva", "alice@example.com");
        restaurant = new Restaurant("R001", "Pizzaria Boa", "pizza@example.com", "R001", "Rua das Flores, 123");
        order = new Order("ORD001", customer, restaurant);
    }

    // --- Testes de Transições Válidas ---

    @Test
    @DisplayName("Deve iniciar no estado CreatedState")
    void shouldStartInCreatedState() {
        assertTrue(order.getCurrentState() instanceof CreatedState);
    }

    @Test
    @DisplayName("Status do pedido recém-criado deve ser 'Criado'")
    void createdOrderShouldHaveCreatedStatus() {
        assertEquals("Criado", order.getCurrentState().getStatus());
    }

    @Test
    @DisplayName("Deve transitar de Created para PaymentState ao chamar nextState")
    void nextStateFromCreatedShouldBePaymentState() {
        order.nextState();
        assertTrue(order.getCurrentState() instanceof PaymentState);
    }

    @Test
    @DisplayName("Status do pedido após transitar de Created para Payment deve ser 'Pagamento'")
    void nextStateStatusFromCreatedShouldBePayment() {
        order.nextState();
        assertEquals("Pagamento", order.getCurrentState().getStatus());
    }

    @Test
    @DisplayName("Deve transitar de Payment para PreparingState ao chamar nextState")
    void nextStateFromPaymentShouldBePreparingState() {
        order.changeState(new PaymentState()); // Força o estado para Payment
        order.nextState();
        assertTrue(order.getCurrentState() instanceof PreparingState);
    }

    @Test
    @DisplayName("Status do pedido após transitar de Payment para Preparing deve ser 'Preparando'")
    void nextStateStatusFromPaymentShouldBePreparing() {
        order.changeState(new PaymentState()); // Força o estado para Payment
        order.nextState();
        assertEquals("Preparando", order.getCurrentState().getStatus());
    }

    @Test
    @DisplayName("Deve transitar de Preparing para ShippingState ao chamar nextState")
    void nextStateFromPreparingShouldBeShippingState() {
        order.changeState(new PreparingState()); // Força o estado para Preparing
        order.nextState();
        assertTrue(order.getCurrentState() instanceof ShippingState);
    }

    @Test
    @DisplayName("Status do pedido após transitar de Preparing para Shipping deve ser 'Em Trânsito'")
    void nextStateStatusFromPreparingShouldBeShipping() {
        order.changeState(new PreparingState()); // Força o estado para Preparing
        order.nextState();
        assertEquals("Em Trânsito", order.getCurrentState().getStatus());
    }

    @Test
    @DisplayName("Deve transitar de Shipping para DeliveredState ao chamar nextState")
    void nextStateFromShippingShouldBeDeliveredState() {
        order.changeState(new ShippingState()); // Força o estado para Shipping
        order.nextState();
        assertTrue(order.getCurrentState() instanceof DeliveredState);
    }

    @Test
    @DisplayName("Status do pedido após transitar de Shipping para Delivered deve ser 'Entregue'")
    void nextStateStatusFromShippingShouldBeDelivered() {
        order.changeState(new ShippingState()); // Força o estado para Shipping
        order.nextState();
        assertEquals("Entregue", order.getCurrentState().getStatus());
    }

    // --- Testes de Cancelamento Válido ---

    @Test
    @DisplayName("Deve transitar de Created para CancelledState ao chamar cancel")
    void cancelFromCreatedShouldBeCancelledState() {
        order.cancel();
        assertTrue(order.getCurrentState() instanceof CancelledState);
    }

    @Test
    @DisplayName("Status do pedido após cancelar do estado Created deve ser 'Cancelado'")
    void cancelStatusFromCreatedShouldBeCancelled() {
        order.cancel();
        assertEquals("Cancelado", order.getCurrentState().getStatus());
    }

    @Test
    @DisplayName("Deve transitar de Payment para CancelledState ao chamar cancel")
    void cancelFromPaymentShouldBeCancelledState() {
        order.changeState(new PaymentState());
        order.cancel();
        assertTrue(order.getCurrentState() instanceof CancelledState);
    }

    @Test
    @DisplayName("Status do pedido após cancelar do estado Payment deve ser 'Cancelado'")
    void cancelStatusFromPaymentShouldBeCancelled() {
        order.changeState(new PaymentState());
        order.cancel();
        assertEquals("Cancelado", order.getCurrentState().getStatus());
    }

    @Test
    @DisplayName("Deve transitar de Preparing para CancelledState ao chamar cancel")
    void cancelFromPreparingShouldBeCancelledState() {
        order.changeState(new PreparingState());
        order.cancel();
        assertTrue(order.getCurrentState() instanceof CancelledState);
    }

    @Test
    @DisplayName("Status do pedido após cancelar do estado Preparing deve ser 'Cancelado'")
    void cancelStatusFromPreparingShouldBeCancelled() {
        order.changeState(new PreparingState());
        order.cancel();
        assertEquals("Cancelado", order.getCurrentState().getStatus());
    }

    @Test
    @DisplayName("Deve transitar de Shipping para CancelledState ao chamar cancel")
    void cancelFromShippingShouldBeCancelledState() {
        order.changeState(new ShippingState());
        order.cancel();
        assertTrue(order.getCurrentState() instanceof CancelledState);
    }

    @Test
    @DisplayName("Status do pedido após cancelar do estado Shipping deve ser 'Cancelado'")
    void cancelStatusFromShippingShouldBeCancelled() {
        order.changeState(new ShippingState());
        order.cancel();
        assertEquals("Cancelado", order.getCurrentState().getStatus());
    }

    // --- Testes de Transições Inválidas (lançando UnsupportedOperationException) ---

    @Test
    @DisplayName("DeliveredState: Deve lançar UnsupportedOperationException ao chamar nextState")
    void deliveredStateNextStateShouldThrowException() {
        order.changeState(new DeliveredState());
        UnsupportedOperationException thrown = assertThrows(UnsupportedOperationException.class, () -> order.nextState());
        assertEquals("Not possible to do anything with order.", thrown.getMessage());
    }

    @Test
    @DisplayName("DeliveredState: Deve lançar UnsupportedOperationException ao chamar cancel")
    void deliveredStateCancelShouldThrowException() {
        order.changeState(new DeliveredState());
        UnsupportedOperationException thrown = assertThrows(UnsupportedOperationException.class, () -> order.cancel());
        assertEquals("Not possible to cancel order.", thrown.getMessage());
    }

    @Test
    @DisplayName("CancelledState: Deve lançar UnsupportedOperationException ao chamar nextState")
    void cancelledStateNextStateShouldThrowException() {
        order.changeState(new CancelledState());
        UnsupportedOperationException thrown = assertThrows(UnsupportedOperationException.class, () -> order.nextState());
        assertEquals("Not possible to do anything with order.", thrown.getMessage());
    }

    @Test
    @DisplayName("CancelledState: Deve lançar UnsupportedOperationException ao chamar cancel")
    void cancelledStateCancelShouldThrowException() {
        order.changeState(new CancelledState());
        UnsupportedOperationException thrown = assertThrows(UnsupportedOperationException.class, () -> order.cancel());
        assertEquals("Not possible to cancel order.", thrown.getMessage());
    }
}