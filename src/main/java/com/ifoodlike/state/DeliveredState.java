package com.ifoodlike.state;

import com.ifoodlike.model.Order;

public class DeliveredState extends OrderState {
    @Override
    public String getStatus() {
        return "Entregue";
    }
}