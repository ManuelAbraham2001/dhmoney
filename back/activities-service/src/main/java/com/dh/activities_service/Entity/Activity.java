package com.dh.activities_service.Entity;

import io.swagger.v3.oas.annotations.media.DependentRequired;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import reactor.util.annotation.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "CHAR(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Schema(description = "Id de la activity", example = "50a419ed-f8f4-496f-ab39-bba0bc60aa0e")
    private UUID id;
    @Column(name = "user_id", nullable = false, columnDefinition = "CHAR(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Schema(description = "Id del usuario", example = "50a419ed-f8f4-496f-ab39-bba0bc60aa0e")
    private UUID userId;
    @NotNull(message = "El monto es obligatorio")
    @Schema(description = "Monto de la activity. Activities de type 'Deposit' debe ser positivo. Activities de type 'Transfer' debe ser negativo.", example = "500")
    private Double amount;
    @Schema(description = "Nombre de la activity", example = "Transferencia a Juan")
    private String name;
    @Schema(description = "Fecha de la activity", example = "07-12-2024")
    private LocalDateTime dated;
    @NotBlank(message = "El tipo de actividad es obligatorio")
    @Pattern(regexp = "Deposit|Transfer", message = "El tipo debe ser 'Deposit' o 'Transfer'")
    @Schema(description = "Tipo de actividad", example = "Deposit|Transfer")
    private String type;
    @Schema(description = "Cuenta de origen", example = "7649387426450947398547")
    private String origin;
    @Schema(description = "Cuenta de destino", example = "9846374091743892649836")
    private String destination;

    public Activity() {
    }

    public Activity(UUID id, UUID userId, Double amount, String name, LocalDateTime dated, String type, String origin, String destination) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.name = name;
        this.dated = dated;
        this.type = type;
        this.origin = origin;
        this.destination = destination;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDated() {
        return dated;
    }

    public void setDated(LocalDateTime dated) {
        this.dated = dated;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
