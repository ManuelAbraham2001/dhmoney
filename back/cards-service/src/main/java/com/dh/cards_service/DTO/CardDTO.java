package com.dh.cards_service.DTO;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

@Schema(name = "Crear Tarjeta",description = "Crear una tarjeta asociada a un usuario.")
public class CardDTO {
    @Hidden
    private UUID id;

    @NotBlank(message = "La fecha de expiración es obligatoria")
    @Pattern(regexp = "^\\d{4}$", message = "La fecha de expiracion tiene un formato invalido")
    @Schema(
            description = "Fecha de expiración de la tarjeta en formato MMYY (mes y año, sin barra).",
            example = "0527"
    )
    private String expiration;

    @NotBlank(message = "El nombre del titular es obligatorio")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "El nombre solo puede contener letras y espacios")
    @Schema(
            description = "Nombre del titular tal como aparece en la tarjeta. Solo letras y espacios.",
            example = "JUAN PEREZ"
    )
    private String name;
    @NotBlank(message = "El número de la tarjeta es obligatorio")
    @Pattern(regexp = "^\\d{16}$", message = "El número de tarjeta debe tener 16 dígitos")
    @Schema(
            description = "Número de la tarjeta, debe tener 16 dígitos sin espacios.",
            example = "1234567812345678"
    )
    private String number;
    @Schema(
            description = "Código de seguridad (CVC), compuesto por 3 dígitos.",
            example = "123"
    )
    @NotBlank(message = "El CVC es obligatorio")
    @Pattern(regexp = "^\\d{3}$", message = "El CVC debe tener 3 dígitos")
    private String cvc;

    public CardDTO() {
    }

    public CardDTO(UUID id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public CardDTO(UUID id, String expiration, String name, String number, String cvc) {
        this.id = id;
        this.expiration = expiration;
        this.name = name;
        this.number = number;
        this.cvc = cvc;
    }

    public CardDTO(String expiration, String name, String number, String cvc) {
        this.expiration = expiration;
        this.name = name;
        this.number = number;
        this.cvc = cvc;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }
}
