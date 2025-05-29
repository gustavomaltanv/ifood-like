package com.ifoodlike.chainofresponsibility;

import com.ifoodlike.model.Customer;
import com.ifoodlike.model.Order;
import com.ifoodlike.request.OrderRequest;
import com.ifoodlike.state.*;
import com.ifoodlike.strategy.PaymentStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CustomerHandler extends OrderRequestHandler {

    private PaymentStrategy defaultPaymentStrategy;
    private final Map<String, Function<OrderRequest, Boolean>> actionHandlers;

    public CustomerHandler(PaymentStrategy defaultPaymentStrategy) {
        this.defaultPaymentStrategy = defaultPaymentStrategy;
        this.actionHandlers = new HashMap<>();
        actionHandlers.put("PLACE_ORDER", this::handlePlaceOrder);
        actionHandlers.put("MAKE_PAYMENT", this::handleMakePayment);
        actionHandlers.put("CANCEL_ORDER", this::handleCancelOrder);
    }

    @Override
    public boolean handleRequest(OrderRequest request) {
        if (request.getUser() instanceof Customer) {
            Function<OrderRequest, Boolean> handler = actionHandlers.get(request.getAction());
            if (handler != null) {
                return handler.apply(request);
            }
            return passToNext(request);
        }
        return passToNext(request);
    }

    private boolean handlePlaceOrder(OrderRequest request) {
        return true;
    }

    private boolean handleMakePayment(OrderRequest request) {
        Order order = request.getOrder();
        if (!(order.getCurrentState() instanceof CreatedState)) {
            return false;
        }
        return order.initiatePayment(request.getAmount(), defaultPaymentStrategy);
    }

    private boolean handleCancelOrder(OrderRequest request) {
        Order order = request.getOrder();
        try {
            order.cancel();
            return true;
        } catch (UnsupportedOperationException e) {
            return false;
        }
    }
}