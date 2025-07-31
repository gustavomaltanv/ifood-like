package com.ifoodlike.command;

import com.ifoodlike.model.Order;

public class NextOrderStateCommand implements Command {
    private Order order;

    public NextOrderStateCommand(Order order) {
        this.order = order;
    }

    @Override
    public void execute() {
        order.nextState();
    }
}

