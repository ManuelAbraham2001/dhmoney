package com.dh.keycloak_service.DTO;

public class JwtResponse {
    private static final String type = "Bearer";
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
