package com.ifoodlike.state;

import com.ifoodlike.model.Order;

public class CancelledState extends OrderState {
    @Override
    public String getStatus() {
        return "Cancelado";
    }
}