package com.ifoodlike.strategy;

import com.ifoodlike.model.Customer;
import com.ifoodlike.model.Order;
import com.ifoodlike.model.Restaurant;
import com.ifoodlike.state.CreatedState;
import com.ifoodlike.state.OrderState;
import com.ifoodlike.state.PaymentState;
import com.ifoodlike.state.CancelledState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*; // Para mockar a estratégia de pagamento, se necessário

public class PaymentStrategyTest {

    private PaymentService paymentService;
    private Order order;
    private Customer customer;
    private Restaurant restaurant;
    private static final double TEST_AMOUNT = 100.0;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService();
        customer = new Customer("C001", "Alice Silva", "alice@example.com");
        restaurant = new Restaurant("R001", "Pizzaria Boa", "pizza@example.com", "R001", "Rua das Flores, 123");
        order = new Order("ORD001", customer, restaurant);
        order.changeState(new CreatedState());
    }

    @Test
    @DisplayName("CreditCardPayment: Deve processar pagamento com sucesso com dados válidos")
    void creditCardPaymentShouldProcessSuccessfullyWithValidData() {
        PaymentStrategy creditCard = new CreditCardPayment("1111222233334444", "John Doe", "12/25", "123");
        paymentService.setPaymentStrategy(creditCard);
        assertTrue(paymentService.executePayment(TEST_AMOUNT, order),
                "O pagamento com cartão de crédito deveria ser bem-sucedido.");
    }

    @Test
    @DisplayName("CreditCardPayment: Deve falhar o pagamento com cartão inválido (ex: vazio)")
    void creditCardPaymentShouldFailWithInvalidCardNumber() {
        PaymentStrategy creditCard = new CreditCardPayment("", "John Doe", "12/25", "123"); // Cartão vazio
        paymentService.setPaymentStrategy(creditCard);
        assertFalse(paymentService.executePayment(TEST_AMOUNT, order),
                "O pagamento com cartão de crédito vazio deveria falhar.");
    }

    @Test
    @DisplayName("PixPayment: Deve processar pagamento com sucesso com chave válida")
    void pixPaymentShouldProcessSuccessfullyWithValidKey() {
        PaymentStrategy pix = new PixPayment("123.456.789-00");
        paymentService.setPaymentStrategy(pix);
        assertTrue(paymentService.executePayment(TEST_AMOUNT, order),
                "O pagamento com PIX deveria ser bem-sucedido.");
    }

    @Test
    @DisplayName("PixPayment: Deve falhar o pagamento com chave inválida (ex: vazia)")
    void pixPaymentShouldFailWithInvalidKey() {
        PaymentStrategy pix = new PixPayment(""); // Chave vazia
        paymentService.setPaymentStrategy(pix);
        assertFalse(paymentService.executePayment(TEST_AMOUNT, order),
                "O pagamento com PIX vazio deveria falhar.");
    }

    @Test
    @DisplayName("PaymentService: Deve lançar IllegalStateException se estratégia não for definida")
    void paymentServiceShouldThrowExceptionIfStrategyNotSet() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class,
                () -> paymentService.executePayment(TEST_AMOUNT, order),
                "Deveria lançar IllegalStateException se a estratégia não for definida.");
        assertEquals("Payment strategy not set. Please set a strategy before executing payment.", thrown.getMessage());
    }

    // --- Testes de Integração Order <-> PaymentService/Strategy ---

    @Test
    @DisplayName("Order: initiatePayment deve transitar para PaymentState em caso de sucesso")
    void orderInitiatePaymentSuccessShouldLeadToPaymentState() {
        PaymentStrategy mockStrategy = mock(PaymentStrategy.class);
        when(mockStrategy.processPayment(anyDouble(), any(Order.class))).thenReturn(true);

        order.initiatePayment(TEST_AMOUNT, mockStrategy);
        assertTrue(order.getCurrentState() instanceof PaymentState,
                "O pedido deveria transitar para PaymentState após pagamento bem-sucedido.");
    }

    @Test
    @DisplayName("Order: initiatePayment deve retornar true em caso de sucesso")
    void orderInitiatePaymentShouldReturnTrueOnSuccess() {
        PaymentStrategy mockStrategy = mock(PaymentStrategy.class);
        when(mockStrategy.processPayment(anyDouble(), any(Order.class))).thenReturn(true);

        assertTrue(order.initiatePayment(TEST_AMOUNT, mockStrategy),
                "initiatePayment deveria retornar true para pagamento bem-sucedido.");
    }

    @Test
    @DisplayName("Order: initiatePayment deve transitar para CancelledState em caso de falha")
    void orderInitiatePaymentFailureShouldLeadToCancelledState() {
        PaymentStrategy mockStrategy = mock(PaymentStrategy.class);
        when(mockStrategy.processPayment(anyDouble(), any(Order.class))).thenReturn(false);

        order.initiatePayment(TEST_AMOUNT, mockStrategy);
        assertTrue(order.getCurrentState() instanceof CancelledState,
                "O pedido deveria transitar para CancelledState após pagamento falho.");
    }

    @Test
    @DisplayName("Order: initiatePayment deve retornar false em caso de falha")
    void orderInitiatePaymentShouldReturnFalseOnFailure() {
        PaymentStrategy mockStrategy = mock(PaymentStrategy.class);
        when(mockStrategy.processPayment(anyDouble(), any(Order.class))).thenReturn(false);

        assertFalse(order.initiatePayment(TEST_AMOUNT, mockStrategy),
                "initiatePayment deveria retornar false para pagamento falho.");
    }

    @Test
    @DisplayName("Order: initiatePayment não deve transitar se o estado inicial não for CreatedState")
    void orderInitiatePaymentShouldNotProceedIfNotCreatedState() {
        order.changeState(new PaymentState()); // Muda o estado para não-Created
        PaymentStrategy mockStrategy = mock(PaymentStrategy.class); // Mock para não executar lógica real

        boolean result = order.initiatePayment(TEST_AMOUNT, mockStrategy);

        assertFalse(result, "initiatePayment deveria retornar false se não estiver no estado Created.");
    }

    @Test
    @DisplayName("Order: initiatePayment não deve mudar o estado se o estado inicial não for CreatedState")
    void orderInitiatePaymentShouldNotChangeStateIfNotCreatedState() {
        OrderState initialState = new PaymentState();
        order.changeState(initialState);

        PaymentStrategy mockStrategy = mock(PaymentStrategy.class);
        order.initiatePayment(TEST_AMOUNT, mockStrategy);

        assertSame(initialState, order.getCurrentState(), "O estado do pedido não deveria mudar se o pagamento não for iniciado do CreatedState.");
    }
}