package com.ifoodlike.chainofresponsibility;

import com.ifoodlike.request.OrderRequest;

public abstract class OrderRequestHandler {
    protected OrderRequestHandler nextHandler;

    public void setNextHandler(OrderRequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract boolean handleRequest(OrderRequest request);

    protected boolean passToNext(OrderRequest request) {
        if (nextHandler != null) {
            return nextHandler.handleRequest(request);
        }
        return false;
    }
}