package com.dh.users_service.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "User", description = "Informacion del usuario")
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

    public UserDTO() {
    }

    public UserDTO(UUID id, String firstName, String lastName, String email, String phone, String dni) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
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
}
