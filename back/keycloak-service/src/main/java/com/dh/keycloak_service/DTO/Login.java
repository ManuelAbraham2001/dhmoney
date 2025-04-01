package com.dh.keycloak_service.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Login")
public class Login {
    @Schema(description = "Email del usuario", example = "usuario@email.com")
    @NotBlank(message = "El email es obligatorio")
    @Email
    private String email;

    @Schema(description = "Contraseña del usuario", example = "Prueba1")
    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,}$", message = "La contraseña debe tener al menos 6 caracteres, 1 letra mayuscula y 1 numero")
    private String password;

    public Login() {
    }

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public @NotBlank(message = "El email es obligatorio") @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "El email es obligatorio") @Email String email) {
        this.email = email;
    }

    public @NotBlank(message = "La contraseña es obligatoria") @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,}$", message = "La contraseña debe tener al menos 6 caracteres, 1 letra mayuscula y 1 numero") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "La contraseña es obligatoria") @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,}$", message = "La contraseña debe tener al menos 6 caracteres, 1 letra mayuscula y 1 numero") String password) {
        this.password = password;
    }
}
