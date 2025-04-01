package com.dh.users_service.DTO;

import java.util.UUID;

public class Account {
    private UUID id;
    private UUID userId;
    private String name;
    private String cvu;
    private String alias;
    private double balance;

    public Account() {
    }

    public Account(UUID id, UUID userId, String name, String cvu, String alias, double balance) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.cvu = cvu;
        this.alias = alias;
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
