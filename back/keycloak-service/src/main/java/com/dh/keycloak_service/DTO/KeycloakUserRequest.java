package com.dh.keycloak_service.DTO;

import java.util.List;

public class KeycloakUserRequest {
    private String username;
    private Boolean enabled;
    private String firstName;
    private String lastName;
    private String email;
    private List<Credential> credentials;

    // Getters y setters


    public KeycloakUserRequest() {
    }

    public KeycloakUserRequest(String username, String firstName, String lastName, String email, List<Credential> credentials) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.credentials = credentials;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Credential> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<Credential> credentials) {
        this.credentials = credentials;
    }
}

