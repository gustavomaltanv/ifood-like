package com.gustavo.observer;

public class User implements IObserver {
    private final String name;
    private final String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public void update(String message) {
        System.out.printf("Notificação para %s (%s): %s%n", name, email, message);
    }
}
