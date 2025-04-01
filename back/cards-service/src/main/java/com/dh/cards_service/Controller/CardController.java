package com.dh.cards_service.Controller;

import com.dh.cards_service.DTO.CardDTO;
import com.dh.cards_service.DTO.ListCardDTO;
import com.dh.cards_service.Entity.Card;
import com.dh.cards_service.Service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cards")
//@CrossOrigin("*")
public class CardController {
    @Autowired
    private CardService cardService;

    @Operation(
            summary = "Crear una tarjeta para un usuario",
            description = "Crea una nueva tarjeta asociada al usuario indicado por su ID. Se deben proporcionar los datos de la tarjeta: número, CVC, fecha de expiración y nombre del titular.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "ID del usuario al que se le asociará la tarjeta.",
                            required = true,
                            in = ParameterIn.PATH,
                            example = "d44f3b10-1234-4abc-8901-abcdef123456"
                    ),
                    @Parameter(
                            name = "Authorization",
                            description = "Token JWT en formato Bearer.",
                            required = true,
                            in = ParameterIn.HEADER,
                            example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la tarjeta a registrar.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CardDTO.class),
                            examples = @ExampleObject(value = "{\n  \"number\": \"1234567812345678\",\n  \"cvc\": \"123\",\n  \"expiration\": \"0527\",\n  \"name\": \"JUAN PEREZ\"\n}")
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Tarjeta creada exitosamente.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Card.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de la tarjeta inválidos.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"El número de tarjeta debe tener 16 dígitos.\" }")
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Tarjeta existente.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"Ya existe una tarjeta con ese numero.\" }")
                    )
            )
    })
    @PostMapping("/{userId}")
    public ResponseEntity<?> create(@PathVariable UUID userId, @Valid @RequestBody CardDTO cardDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.create(cardDTO, userId));
    }

    @Operation(
            summary = "Listar tarjetas de un usuario",
            description = "Devuelve una lista de todas las tarjetas asociadas al usuario indicado por su ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "ID del usuario cuyas tarjetas se desean listar.",
                            required = true,
                            in = ParameterIn.PATH,
                            example = "d44f3b10-1234-4abc-8901-abcdef123456"
                    ),
                    @Parameter(
                            name = "Authorization",
                            description = "Token JWT en formato Bearer.",
                            required = true,
                            in = ParameterIn.HEADER,
                            example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de tarjetas obtenido correctamente.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ListCardDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno al obtener las tarjetas.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"Ocurrió un error inesperado al obtener las tarjetas.\" }")
                    )
            )
    })
    @GetMapping("/{userId}")
    public ResponseEntity<?> listCards(@PathVariable UUID userId) {
        try {
            List<ListCardDTO> cards = cardService.listCards(userId);
            return ResponseEntity.ok(cards);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            summary = "Eliminar una tarjeta",
            description = "Elimina la tarjeta identificada por su ID. La operación es irreversible.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "cardId",
                            description = "ID de la tarjeta a eliminar.",
                            required = true,
                            in = ParameterIn.PATH,
                            example = "d44f3b10-1234-4abc-8901-abcdef123456"
                    ),
                    @Parameter(
                            name = "Authorization",
                            description = "Token JWT en formato Bearer.",
                            required = true,
                            in = ParameterIn.HEADER,
                            example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tarjeta eliminada exitosamente."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró una tarjeta con el ID proporcionado.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"No existe una tarjeta con el ID especificado.\" }")
                    )
            )
    })
    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable UUID cardId) {
        cardService.deleteCard(cardId);
        return ResponseEntity.ok().build();
    }

}
