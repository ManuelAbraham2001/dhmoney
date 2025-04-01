package com.dh.accounts_service.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "CHAR(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Schema(
            description = "ID único de la cuenta.",
            example = "a12f3c4e-5678-90ab-cdef-1234567890ab"
    )
    private UUID id;
    @Column(name = "user_id", nullable = false, unique = true, columnDefinition = "CHAR(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Schema(
            description = "ID del usuario al que pertenece la cuenta.",
            example = "b98f1e7d-4321-09ba-cdef-0987654321fe"
    )
    private UUID userId;
    @Schema(
            description = "Nombre completo del titular de la cuenta.",
            example = "Juan Pérez"
    )
    private String name;
    @Schema(
            description = "CVU de 22 dígitos de la cuenta.",
            example = "0001234500001234567890"
    )
    private String cvu;
    @Schema(
            description = "Alias de la cuenta formado por tres palabras separadas por puntos. Cada palabra puede tener hasta 10 caracteres.",
            example = "alias.de.prueba"
    )
    private String alias;
    @Schema(
            description = "Saldo actual disponible en la cuenta.",
            example = "12500.75"
    )
    private Double balance;

    public Account() {
    }

    public Account(UUID id, UUID userId, String name, String cvu, String alias, Double balance) {
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

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", cvu='" + cvu + '\'' +
                ", alias='" + alias + '\'' +
                ", balance=" + balance +
                '}';
    }
}
