package com.ifoodlike.chainofresponsibility;

import com.ifoodlike.model.DeliveryDriver;
import com.ifoodlike.model.Order;
import com.ifoodlike.request.OrderRequest;
import com.ifoodlike.state.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DeliveryDriverHandler extends OrderRequestHandler {

    private final Map<String, Function<OrderRequest, Boolean>> actionHandlers;

    public DeliveryDriverHandler() {
        this.actionHandlers = new HashMap<>();
        actionHandlers.put("DELIVER_ORDER", this::handleDeliverOrder);
    }

    @Override
    public boolean handleRequest(OrderRequest request) {
        if (request.getUser() instanceof DeliveryDriver) {
            Function<OrderRequest, Boolean> handler = actionHandlers.get(request.getAction());
            if (handler != null) {
                return handler.apply(request);
            }
            return passToNext(request);
        }
        return passToNext(request);
    }

    private boolean handleDeliverOrder(OrderRequest request) {
        Order order = request.getOrder();
        if (!(order.getCurrentState() instanceof ShippingState)) {
            return false;
        }
        try {
            order.nextState();
            return true;
        } catch (UnsupportedOperationException e) {
            return false;
        }
    }
}