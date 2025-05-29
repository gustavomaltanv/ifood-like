package com.ifoodlike.chainofresponsibility;

import com.ifoodlike.model.Order;
import com.ifoodlike.model.Restaurant;
import com.ifoodlike.request.OrderRequest;
import com.ifoodlike.state.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class RestaurantHandler extends OrderRequestHandler {

    private final Map<String, Function<OrderRequest, Boolean>> actionHandlers;

    public RestaurantHandler() {
        this.actionHandlers = new HashMap<>();
        actionHandlers.put("PREPARE_ORDER", this::handlePrepareOrder);
        actionHandlers.put("READY_FOR_SHIPPING", this::handleReadyForShipping);
        actionHandlers.put("CANCEL_ORDER", this::handleCancelOrder);
    }

    @Override
    public boolean handleRequest(OrderRequest request) {
        if (request.getUser() instanceof Restaurant) {
            Function<OrderRequest, Boolean> handler = actionHandlers.get(request.getAction());
            if (handler != null) {
                return handler.apply(request);
            }
            return passToNext(request);
        }
        return passToNext(request);
    }

    private boolean handlePrepareOrder(OrderRequest request) {
        Order order = request.getOrder();
        if (order.getCurrentState() instanceof PreparingState) {
            return true;
        }
        if (!(order.getCurrentState() instanceof PaymentState) && !(order.getCurrentState() instanceof CreatedState)) {
            return false;
        }
        order.changeState(new PreparingState());
        return true;
    }

    private boolean handleReadyForShipping(OrderRequest request) {
        Order order = request.getOrder();
        if (order.getCurrentState() instanceof ShippingState) {
            return true;
        }
        if (!(order.getCurrentState() instanceof PreparingState)) {
            return false;
        }
        order.changeState(new ShippingState());
        return true;
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