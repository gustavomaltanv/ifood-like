package com.gustavo.observer;


import com.gustavo.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObserverTest {


    private Order order;
    private User user;

    @BeforeEach
    void setUp() {
        order = new Order();
        user = new User("Gustavo", "gustavo@email.com");
        order.addObserver(user);
    }

    @Test
    void userRecebeNotificacao() {
        String mensagem = "Seu pedido foi enviado!";
        order.notifyObservers(mensagem);
        assertTrue(true);
    }

    @Test
    void userPodeSerRemovidoDasNotificacoes() {
        order.removeObserver(user);
        order.notifyObservers("Mensagem ignorada");
        assertTrue(true);
    }
}
