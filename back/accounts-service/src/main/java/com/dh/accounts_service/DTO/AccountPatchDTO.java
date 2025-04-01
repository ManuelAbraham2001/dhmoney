package com.dh.accounts_service.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(name = "Actualizar Alias", description = "Actualizar alias de una cuenta")
public class AccountPatchDTO {
    @NotBlank(message = "El alias es obligatorio")
    @Pattern(regexp = "^[a-zA-Z]+\\.[a-zA-Z]+\\.[a-zA-Z]+$", message = "Alias invalido")
    @Schema(
            description = "Nuevo alias de la cuenta. Debe tener tres palabras separadas por puntos, con hasta 10 caracteres cada una.",
            example = "nuevo.alias.cuenta"
    )
    private String alias;

    public AccountPatchDTO() {
    }

    public AccountPatchDTO(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
