package com.gustavo.chain;

import com.gustavo.Order;
import com.gustavo.observer.User;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class HandlerTest {

    private Order order;
    private User user;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        order = new Order();
        user = new User("Gustavo", "gustavo@email.com");
        order.addObserver(user);

        originalOut = System.out;
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testChainCompletaExecutaTodasAsEtapas() {
        Handler payment = new PaymentHandler();
        Handler packing = new PackingHandler();
        Handler shipping = new ShippingHandler();

        payment.setNext(packing);
        packing.setNext(shipping);

        order.setHandlerChain(payment);

        order.process();

        String output = outputStreamCaptor.toString().trim();

        assertTrue(output.contains("Pagamento processado com sucesso."));
        assertTrue(output.contains("Pedido empacotado."));
        assertTrue(output.contains("Pedido enviado para transporte."));
    }

    @Test
    void testHandlerUnicoExecutaSomenteUmaEtapa() {
        Handler payment = new PaymentHandler();

        order.setHandlerChain(payment);

        order.process();

        String output = outputStreamCaptor.toString().trim();

        assertTrue(output.contains("Pagamento processado com sucesso."));
        assertFalse(output.contains("Pedido empacotado."));
        assertFalse(output.contains("Pedido enviado para transporte."));
    }

    @Test
    void testHandlerParcialExecutaDuasEtapas() {
        Handler payment = new PaymentHandler();
        Handler packing = new PackingHandler();

        payment.setNext(packing);
        order.setHandlerChain(payment);

        order.process();

        String output = outputStreamCaptor.toString().trim();

        assertTrue(output.contains("Pagamento processado com sucesso."));
        assertTrue(output.contains("Pedido empacotado."));
        assertFalse(output.contains("Pedido enviado para transporte."));
    }
}
