package com.gustavo.state;

import com.gustavo.Order;
import com.gustavo.observer.User;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class OrderStateTest {

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
    void testCreatedStateTransicaoParaProcessingState() {
        order.setState(CreatedState.getInstance());

        order.nextStep();

        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Criado"));
        assertTrue(output.contains("Processando"));
        assertEquals(ProcessingState.getInstance(), order.getState());
    }

    @Test
    void testProcessingStateTransicaoParaShippedState() {
        order.setState(ProcessingState.getInstance());

        order.nextStep();

        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Processando"));
        assertTrue(output.contains("Enviado"));
        assertEquals(ShippedState.getInstance(), order.getState());
    }

    @Test
    void testShippedStateTransicaoParaDeliveredState() {
        order.setState(ShippedState.getInstance());

        order.nextStep();

        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Enviado"));
        assertTrue(output.contains("Entregue"));
        assertEquals(DeliveredState.getInstance(), order.getState());
    }

    @Test
    void testDeliveredStateNaoTransita() {
        order.setState(DeliveredState.getInstance());

        order.nextStep();

        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Entregue"));
        assertEquals(DeliveredState.getInstance(), order.getState());
    }
}
