package com.dh.accounts_service.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class ActivityDTO {
    private Double amount;
    private String name;
    private LocalDateTime dated;
    private String type;
    private String origin;
    private String destination;

    public ActivityDTO() {
    }

    public ActivityDTO(Double amount, String name, LocalDateTime dated, String type, String origin, String destination) {
        this.amount = amount;
        this.name = name;
        this.dated = dated;
        this.type = type;
        this.origin = origin;
        this.destination = destination;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDated() {
        return dated;
    }

    public void setDated(LocalDateTime dated) {
        this.dated = dated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
