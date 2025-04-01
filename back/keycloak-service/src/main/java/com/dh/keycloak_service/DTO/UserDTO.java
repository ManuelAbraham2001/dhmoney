package com.dh.keycloak_service.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;
import java.util.UUID;

@Schema(description = "Register")
public class UserDTO {
    @Schema(description = "ID", example = "50a419ed-f8f4-496f-ab39-bba0bc60aa0e")
    private UUID id;
    @Schema(description = "firstName", example = "Juan")
    private String firstName;
    @Schema(description = "lastName", example = "Perez")
    private String lastName;
    @Schema(description = "email", example = "juanperez@email.com")
    private String email;
    @Schema(description = "phone", example = "1167349872")
    private String phone;
    @Schema(description = "dni", example = "45678340")
    private String dni;
    @Schema(description = "accountId", example = "0e27d442-b6bb-4e37-8f61-ea6e5a37a801")
    private UUID accountId;
    @Schema(description = "name", example = "Juan Perez")
    private String name;
    @Schema(description = "cvu", example = "9612729038626916485134")
    private String cvu;
    @Schema(description = "alias", example = "digital.money.house")
    private String alias;
    @Schema(description = "balance", example = "0.0")
    private Double balance;
    private String accessToken;

    public UserDTO() {
    }

    public UserDTO(UUID id, String firstName, String lastName, String email, String phone, String dni, UUID accountId, String name, String cvu, String alias, Double balance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dni = dni;
        this.accountId = accountId;
        this.name = name;
        this.cvu = cvu;
        this.alias = alias;
        this.balance = balance;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
