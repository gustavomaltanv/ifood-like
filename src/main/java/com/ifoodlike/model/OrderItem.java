package com.ifoodlike.model;

public class OrderItem {
    private String nome;

    public OrderItem(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
