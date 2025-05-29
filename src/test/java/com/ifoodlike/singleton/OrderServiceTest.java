package com.ifoodlike.singleton;

import com.ifoodlike.model.Customer;
import com.ifoodlike.model.Order;
import com.ifoodlike.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    private Customer customer;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        OrderService.getInstance().clearOrders();

        customer = new Customer("C001", "Alice Silva", "alice@example.com");
        restaurant = new Restaurant("R001", "Pizzaria Boa", "pizza@example.com", "R001", "Rua das Flores, 123");
    }

    @Test
    @DisplayName("Deve garantir que getInstance retorna uma instância não nula")
    void getInstanceShouldReturnNotNullInstance() {
        assertNotNull(OrderService.getInstance(), "getInstance deveria retornar uma instância não nula.");
    }

    @Test
    @DisplayName("Deve garantir que as múltiplas chamadas a getInstance retornam a mesma instância")
    void getInstanceShouldReturnSameInstanceOnMultipleCalls() {
        OrderService instance1 = OrderService.getInstance();
        OrderService instance2 = OrderService.getInstance();
        assertSame(instance1, instance2, "As instâncias de OrderService deveriam ser a mesma.");
    }

    @Test
    @DisplayName("Deve permitir adicionar um pedido e verificar o contador de pedidos")
    void shouldAllowAddingOrderAndVerifyCount() {
        OrderService service = OrderService.getInstance();
        Order order = new Order("ORD001", customer, restaurant);
        service.placeOrder(order);
        assertEquals(1, service.getOrderCount(), "Deveria haver 1 pedido no serviço.");
    }

    @Test
    @DisplayName("Deve permitir adicionar um pedido e recuperá-lo pelo ID")
    void shouldAllowAddingOrderAndRetrieveById() {
        OrderService service = OrderService.getInstance();
        Order order = new Order("ORD001", customer, restaurant);
        service.placeOrder(order);
        assertNotNull(service.getOrderById("ORD001"), "O pedido deveria ser recuperável pelo ID.");
    }

    @Test
    @DisplayName("Deve recuperar um pedido existente e ser a mesma instância")
    void shouldRetrieveExistingOrderAndBeSameInstance() {
        OrderService service = OrderService.getInstance();
        Order order = new Order("ORD002", customer, restaurant);
        service.placeOrder(order);
        Order retrievedOrder = service.getOrderById("ORD002");
        assertSame(order, retrievedOrder, "O pedido recuperado deveria ser a mesma instância do pedido original.");
    }

    @Test
    @DisplayName("Deve retornar null para um pedido que não existe")
    void shouldReturnNullForNonExistentOrder() {
        OrderService service = OrderService.getInstance();
        assertNull(service.getOrderById("NON_EXISTENT_ORDER"), "Um pedido não existente deveria retornar null.");
    }

    @Test
    @DisplayName("Deve impedir a adição de pedidos com IDs duplicados, lançando exceção")
    void shouldPreventAddingDuplicateOrdersByThrowingException() {
        OrderService service = OrderService.getInstance();
        Order order1 = new Order("ORD003", customer, restaurant);
        Order order2 = new Order("ORD003", customer, restaurant); // Mesmo ID

        service.placeOrder(order1);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.placeOrder(order2),
                "Deveria lançar IllegalArgumentException para ID duplicado."
        );
    }

    @Test
    @DisplayName("Após falha na adição de pedido duplicado, o contador de pedidos deve permanecer inalterado")
    void orderCountShouldRemainUnchangedAfterDuplicateAddFailure() {
        OrderService service = OrderService.getInstance();
        Order order1 = new Order("ORD003", customer, restaurant);
        Order order2 = new Order("ORD003", customer, restaurant); // Mesmo ID

        service.placeOrder(order1);

        assertThrows(IllegalArgumentException.class, () -> service.placeOrder(order2)); // Gatilha a exceção

        assertEquals(1, service.getOrderCount(), "Deveria haver apenas 1 pedido no serviço após a falha.");
    }

    @Test
    @DisplayName("Deve permitir remover um pedido e retornar true")
    void shouldAllowRemovingOrderAndReturnTrue() {
        OrderService service = OrderService.getInstance();
        Order order = new Order("ORD004", customer, restaurant);
        service.placeOrder(order);
        assertTrue(service.removeOrder("ORD004"), "O pedido deveria ter sido removido com sucesso.");
    }

    @Test
    @DisplayName("Após remover um pedido, ele não deve ser recuperável")
    void removedOrderShouldNotBeRetrievable() {
        OrderService service = OrderService.getInstance();
        Order order = new Order("ORD004", customer, restaurant);
        service.placeOrder(order);
        service.removeOrder("ORD004");
        assertNull(service.getOrderById("ORD004"), "O pedido removido não deveria ser recuperável.");
    }

    @Test
    @DisplayName("Após remover um pedido, o contador deve ser zero")
    void orderCountShouldBeZeroAfterRemovingOrder() {
        OrderService service = OrderService.getInstance();
        Order order = new Order("ORD004", customer, restaurant);
        service.placeOrder(order);
        service.removeOrder("ORD004");
        assertEquals(0, service.getOrderCount(), "Não deveria haver pedidos após a remoção.");
    }

    @Test
    @DisplayName("Deve retornar false ao tentar remover um pedido que não existe")
    void shouldReturnFalseWhenRemovingNonExistentOrder() {
        OrderService service = OrderService.getInstance();
        assertFalse(service.removeOrder("NON_EXISTENT_ORDER"), "Deveria retornar false ao tentar remover um pedido não existente.");
    }
}