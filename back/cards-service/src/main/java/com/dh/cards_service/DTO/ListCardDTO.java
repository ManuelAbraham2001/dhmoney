package com.dh.cards_service.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "Lista de tarjetas", description = "Información de una tarjeta asociada a un usuario.")
public class ListCardDTO {
    @Schema(
            description = "ID único de la tarjeta.",
            example = "d44f3b10-1234-4abc-8901-abcdef123456"
    )
    private UUID id;
    @Schema(
            description = "Nombre del titular tal como aparece en la tarjeta. Solo letras y espacios.",
            example = "JUAN PEREZ"
    )
    private String name;
    @Schema(
            description = "Número de la tarjeta, 16 dígitos sin espacios.",
            example = "1234567812345678"
    )
    private String number;
    @Schema(
            description = "Fecha de expiración de la tarjeta en formato MMYY (mes y año, sin barra).",
            example = "0527"
    )
    private String expiration;

    public ListCardDTO() {
    }

    public ListCardDTO(UUID id, String name, String number, String expiration) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.expiration = expiration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
