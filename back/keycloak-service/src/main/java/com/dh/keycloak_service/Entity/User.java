package com.dh.keycloak_service.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;
@Schema(description = "Register")
public class User {
    private UUID id;

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "El nombre solo puede contener letras y espacios")
    @Schema(description = "Nombre", example = "Juan")
    private String firstName;

    @Schema(description = "Apellido", example = "Perez")
    @NotBlank(message = "El apellido es obligatorio")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "El apellido solo puede contener letras y espacios")
    private String lastName;

    @Schema(description = "Email", example = "juanperez@email.com")
    @NotBlank(message = "El email es obligatorio")
    @Email
    private String email;

    @Schema(description = "Contraseña del usuario", example = "Prueba1")
    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,}$", message = "La contraseña debe tener al menos 6 caracteres, 1 letra mayuscula y 1 numero")
    private String password;


    @Schema(description = "Telefono", example = "1167349872")
    @NotBlank(message = "El telefono es obligatorio")
    @Pattern(regexp = "^\\d+$", message = "El telefono debe contener solo numeros")
    private String phone;

    @Schema(description = "DNI", example = "45678340")
    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^\\d{7,8}$", message = "El DNI debe tener 7 u 8 digitos numericos")
    private String dni;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(UUID id, String firstName, String lastName, String email, String phone, String password, String dni) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.dni = dni;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
