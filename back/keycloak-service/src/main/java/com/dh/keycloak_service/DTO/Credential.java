package com.dh.keycloak_service.DTO;

public class Credential {
    private String type = "password";
    private String value;
    private boolean temporary = false;

    public Credential() {}

    public Credential(String password) {
        this.value = password;
    }

    // Getters y setters

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isTemporary() {
        return temporary;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }
}

