package com.dh.activities_service.Controller;

import com.dh.activities_service.Entity.Activity;
import com.dh.activities_service.Service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/activities")
//@CrossOrigin("*")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Operation(
            summary = "Registrar una nueva actividad para un usuario",
            description = "Crea una nueva actividad (Deposit o Transfer) para un usuario específico.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "ID del usuario",
                            required = true,
                            in = ParameterIn.PATH,
                            example = "50a419ed-f8f4-496f-ab39-bba0bc60aa0e"
                    ),
                    @Parameter(
                            name = "Authorization",
                            description = "Token JWT Bearer",
                            required = true,
                            in = ParameterIn.HEADER,
                            example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos de la actividad a registrar",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Activity.class)
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Actividad creada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Activity.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos o campos faltantes",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"El monto es obligatorio\" }")
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autorizado o token inválido",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"Token inválido o ausente\" }")
                    )
            )
    })
    @PostMapping("/{userId}")
    public ResponseEntity<?> registerActivity(
            @PathVariable UUID userId,
            @Valid @RequestBody Activity activity,
            @RequestHeader("Authorization") String token
    ) {
        Activity activityDB = activityService.create(activity, userId, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(activityDB);
    }
    @Operation(
            summary = "Obtener las actividades de un usuario",
            description = "Devuelve todas las actividades registradas por el usuario. Si se especifica el parámetro `limit`, solo se devuelven las últimas actividades.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "ID del usuario",
                            required = true,
                            in = ParameterIn.PATH,
                            example = "50a419ed-f8f4-496f-ab39-bba0bc60aa0e"
                    ),
                    @Parameter(
                            name = "Authorization",
                            description = "Token JWT en formato Bearer",
                            required = true,
                            in = ParameterIn.HEADER,
                            example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                    ),
                    @Parameter(
                            name = "limit",
                            description = "Cantidad de actividades recientes a devolver",
                            required = false,
                            in = ParameterIn.QUERY,
                            example = "5"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Actividades encontradas",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Activity.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID del usuario no es un UUID válido o parámetros incorrectos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"El ID proporcionado no es válido.\" }")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró el usuario",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"No existe un usuario con el id: 50a419ed-f8f4-496f-ab39-bba0bc60aa0e\" }")
                    )
            )
    })
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getActivitiesByUser(
            @PathVariable UUID userId,
            @RequestParam(required = false) Integer limit
    ) {
        if (limit != null) {
            return ResponseEntity.ok(activityService.getLastFiveActivitiesByUser(userId, limit));
        }
        return ResponseEntity.ok(activityService.getActivitiesByUser(userId));
    }

    @Operation(
            summary = "Obtener una actividad por ID",
            description = "Devuelve una actividad específica usando su ID único.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "activityId",
                            description = "ID de la actividad a buscar",
                            required = true,
                            in = ParameterIn.PATH,
                            example = "f17e3f44-b97c-4f5f-b45f-10a9993c9a02"
                    ),
                    @Parameter(
                            name = "Authorization",
                            description = "Token JWT en formato Bearer",
                            required = true,
                            in = ParameterIn.HEADER,
                            example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Actividad encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Activity.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID no es un UUID válido",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"El ID proporcionado no es un UUID válido.\" }")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró una actividad con ese ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"No existe una actividad con el id: f17e3f44-b97c-4f5f-b45f-10a9993c9a02\" }")
                    )
            )
    })
    @GetMapping("/{activityId}")
    public ResponseEntity<?> getActivityById(@PathVariable UUID activityId) {
        return ResponseEntity.ok(activityService.getActivityById(activityId));
    }

}
