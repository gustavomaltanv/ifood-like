package com.ifoodlike.chainofresponsibility;

import com.ifoodlike.model.Customer;
import com.ifoodlike.model.DeliveryDriver;
import com.ifoodlike.model.Order;
import com.ifoodlike.model.Restaurant;
import com.ifoodlike.request.OrderRequest;
import com.ifoodlike.state.*; // Importar todos os estados
import com.ifoodlike.strategy.CreditCardPayment;
import com.ifoodlike.strategy.PaymentStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderRequestHandlerTest {

    private Customer customer;
    private Restaurant restaurant;
    private DeliveryDriver deliveryDriver;
    private Order order;
    private OrderRequestHandler chain;
    private PaymentStrategy mockPaymentStrategy;

    @BeforeEach
    void setUp() {
        customer = new Customer("C001", "Alice Silva", "alice@example.com");
        restaurant = new Restaurant("R001", "Pizzaria Boa", "pizza@example.com", "R001", "Rua das Flores, 123");
        deliveryDriver = new DeliveryDriver("D001", "Bob Entregador", "bob@example.com", "D001", "Moto X");
        order = new Order("ORD001", customer, restaurant);

        mockPaymentStrategy = new CreditCardPayment("123", "Mock User", "12/25", "123");

        CustomerHandler customerHandler = new CustomerHandler(mockPaymentStrategy);
        RestaurantHandler restaurantHandler = new RestaurantHandler();
        DeliveryDriverHandler deliveryDriverHandler = new DeliveryDriverHandler();

        customerHandler.setNextHandler(restaurantHandler);
        restaurantHandler.setNextHandler(deliveryDriverHandler);

        chain = customerHandler;
    }

    @Test
    @DisplayName("CustomerHandler: Deve processar 'PLACE_ORDER' e retornar true")
    void customerHandlerShouldProcessPlaceOrder() {
        OrderRequest request = new OrderRequest(order, customer, "PLACE_ORDER");
        assertTrue(chain.handleRequest(request),
                "CustomerHandler deveria processar PLACE_ORDER e retornar true.");
    }

    @Test
    @DisplayName("CustomerHandler: Deve processar 'MAKE_PAYMENT' e transitar para PaymentState")
    void customerHandlerShouldProcessMakePaymentAndTransitionToPaymentState() {
        order.changeState(new CreatedState());
        OrderRequest request = new OrderRequest(order, customer, "MAKE_PAYMENT", 50.0);

        chain.handleRequest(request);

        assertTrue(order.getCurrentState() instanceof PaymentState,
                "Após MAKE_PAYMENT, o pedido deveria estar em PaymentState.");
    }

    @Test
    @DisplayName("CustomerHandler: Deve processar 'MAKE_PAYMENT' e retornar true em caso de sucesso")
    void customerHandlerShouldProcessMakePaymentAndReturnTrueOnSuccess() {
        order.changeState(new CreatedState());
        OrderRequest request = new OrderRequest(order, customer, "MAKE_PAYMENT", 50.0);

        assertTrue(chain.handleRequest(request),
                "MAKE_PAYMENT deveria retornar true em caso de sucesso.");
    }

    @Test
    @DisplayName("CustomerHandler: Deve processar 'CANCEL_ORDER' e transitar para CancelledState")
    void customerHandlerShouldProcessCancelOrderAndTransitionToCancelledState() {
        order.changeState(new CreatedState());
        OrderRequest request = new OrderRequest(order, customer, "CANCEL_ORDER");

        chain.handleRequest(request);

        assertTrue(order.getCurrentState() instanceof CancelledState,
                "Após CANCEL_ORDER, o pedido deveria estar em CancelledState.");
    }

    @Test
    @DisplayName("CustomerHandler: Deve processar 'CANCEL_ORDER' e retornar true em caso de sucesso")
    void customerHandlerShouldProcessCancelOrderAndReturnTrueOnSuccess() {
        order.changeState(new CreatedState());
        OrderRequest request = new OrderRequest(order, customer, "CANCEL_ORDER");

        assertTrue(chain.handleRequest(request),
                "CANCEL_ORDER deveria retornar true em caso de sucesso.");
    }

    @Test
    @DisplayName("RestaurantHandler: Deve processar 'PREPARE_ORDER' e transitar para PreparingState")
    void restaurantHandlerShouldProcessPrepareOrderAndTransitionToPreparingState() {
        order.changeState(new PaymentState());
        OrderRequest request = new OrderRequest(order, restaurant, "PREPARE_ORDER");

        chain.handleRequest(request);

        assertTrue(order.getCurrentState() instanceof PreparingState,
                "Após PREPARE_ORDER, o pedido deveria estar em PreparingState.");
    }

    @Test
    @DisplayName("RestaurantHandler: Deve processar 'PREPARE_ORDER' e retornar true")
    void restaurantHandlerShouldProcessPrepareOrderAndReturnTrue() {
        order.changeState(new PaymentState());
        OrderRequest request = new OrderRequest(order, restaurant, "PREPARE_ORDER");

        assertTrue(chain.handleRequest(request),
                "PREPARE_ORDER deveria retornar true.");
    }

    @Test
    @DisplayName("RestaurantHandler: Deve processar 'READY_FOR_SHIPPING' e transitar para ShippingState")
    void restaurantHandlerShouldProcessReadyForShippingAndTransitionToShippingState() {
        order.changeState(new PreparingState());
        OrderRequest request = new OrderRequest(order, restaurant, "READY_FOR_SHIPPING");

        chain.handleRequest(request);

        assertTrue(order.getCurrentState() instanceof ShippingState,
                "Após READY_FOR_SHIPPING, o pedido deveria estar em ShippingState.");
    }

    @Test
    @DisplayName("RestaurantHandler: Deve processar 'READY_FOR_SHIPPING' e retornar true")
    void restaurantHandlerShouldProcessReadyForShippingAndReturnTrue() {
        order.changeState(new PreparingState());
        OrderRequest request = new OrderRequest(order, restaurant, "READY_FOR_SHIPPING");

        assertTrue(chain.handleRequest(request),
                "READY_FOR_SHIPPING deveria retornar true.");
    }

    @Test
    @DisplayName("DeliveryDriverHandler: Deve processar 'DELIVER_ORDER' e transitar para DeliveredState")
    void deliveryDriverHandlerShouldProcessDeliverOrderAndTransitionToDeliveredState() {
        order.changeState(new ShippingState());
        OrderRequest request = new OrderRequest(order, deliveryDriver, "DELIVER_ORDER");

        chain.handleRequest(request);

        assertTrue(order.getCurrentState() instanceof DeliveredState,
                "Após DELIVER_ORDER, o pedido deveria estar em DeliveredState.");
    }

    @Test
    @DisplayName("DeliveryDriverHandler: Deve processar 'DELIVER_ORDER' e retornar true")
    void deliveryDriverHandlerShouldProcessDeliverOrderAndReturnTrue() {
        order.changeState(new ShippingState());
        OrderRequest request = new OrderRequest(order, deliveryDriver, "DELIVER_ORDER");

        assertTrue(chain.handleRequest(request),
                "DELIVER_ORDER deveria retornar true.");
    }

    @Test
    @DisplayName("Requisição com usuário e ação desconhecidos não deve ser processada e retornar false")
    void unknownUserAndActionShouldNotBeProcessed() {
        OrderRequest request = new OrderRequest(order, new Customer("C999", "Unknown", "unknown@example.com"), "UNKNOWN_ACTION");
        assertFalse(chain.handleRequest(request),
                "Requisição com ação desconhecida deveria retornar false (não tratada).");
    }

    @Test
    @DisplayName("Requisição de Cliente para ação de Restaurante não deve ser processada pela cadeia e retornar false")
    void clientRequestForRestaurantActionShouldNotBeProcessedByChain() {
        OrderRequest request = new OrderRequest(order, customer, "PREPARE_ORDER");

        assertFalse(chain.handleRequest(request),
                "Uma ação de PREPARE_ORDER por um cliente não deveria ser processada por nenhum handler da cadeia.");
    }

    @Test
    @DisplayName("Requisição de Cliente para ação de Restaurante não deve alterar o estado do pedido")
    void clientRequestForRestaurantActionShouldNotChangeOrderState() {
        OrderState originalState = order.getCurrentState();
        OrderRequest request = new OrderRequest(order, customer, "PREPARE_ORDER");

        chain.handleRequest(request);

        assertSame(originalState, order.getCurrentState(),
                "O estado do pedido não deveria mudar se a requisição não foi tratada pela cadeia.");
    }
}