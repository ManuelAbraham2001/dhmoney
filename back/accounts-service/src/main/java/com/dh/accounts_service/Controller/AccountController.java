package com.dh.accounts_service.Controller;

import com.dh.accounts_service.DTO.AccountPatchDTO;
import com.dh.accounts_service.DTO.ActivityDTO;
import com.dh.accounts_service.DTO.CreateAccountDTO;
import com.dh.accounts_service.Entity.Account;
import com.dh.accounts_service.Service.AccountService;
import io.swagger.v3.oas.annotations.Hidden;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
//@CrossOrigin("*")
public class AccountController {
    @Autowired
    private AccountService accountService;

//    @Hidden
    @PostMapping("/create/{id}")
    public ResponseEntity<?> create(@PathVariable UUID id, @RequestBody CreateAccountDTO createAccountDTO){
        Account accountDB = accountService.create(id, createAccountDTO);
        return ResponseEntity.ok(accountDB);
    }

    @Operation(
            summary = "Obtener cuenta por ID de usuario",
            description = "Devuelve los datos de la cuenta asociada al usuario especificado por su ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "ID del usuario cuya cuenta se desea obtener.",
                            required = true,
                            in = ParameterIn.PATH,
                            example = "b98f1e7d-4321-09ba-cdef-0987654321fe"
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
                    description = "Cuenta encontrada exitosamente.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Account.class)
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
                    description = "No se encontró una cuenta asociada a ese ID de usuario.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"No existe una cuenta con el ID de usuario proporcionado.\" }")
                    )
            )
    })
    @GetMapping("/{userId}")
    public ResponseEntity<?> findById(@PathVariable UUID userId) {
        Account account = accountService.getAccountById(userId).orElseThrow();
        return ResponseEntity.ok(account);
    }


    @Operation(
            summary = "Buscar cuenta por CVU o alias",
            description = "Devuelve los datos de una cuenta a partir de su CVU (22 dígitos) o alias (3 palabras separadas por puntos, hasta 10 caracteres cada una).",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "cvuOrAlias",
                            description = "CVU o alias de la cuenta a buscar. El CVU debe tener 22 dígitos. El alias debe tener el formato palabra.palabra.palabra, con hasta 10 caracteres por palabra.",
                            required = true,
                            in = ParameterIn.PATH,
                            examples = {
                                    @ExampleObject(
                                            name = "CVU",
                                            summary = "Ejemplo de CVU",
                                            value = "0001234500001234567890"
                                    ),
                                    @ExampleObject(
                                            name = "Alias",
                                            summary = "Ejemplo de alias",
                                            value = "alias.de.prueba"
                                    )
                            }
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
                    description = "Cuenta encontrada exitosamente.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Account.class)
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
                    description = "No se encontró una cuenta con el CVU o alias proporcionado.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"No existe una cuenta con el CVU o alias especificado.\" }")
                    )
            )
    })
    @GetMapping("/cvu/alias/{cvuOrAlias}")
    public ResponseEntity<?> findByCvuOrAlias(@PathVariable String cvuOrAlias) {
        Account account = accountService.getAccountByCvuOrAlias(cvuOrAlias);
        return ResponseEntity.ok(account);
    }


    @Operation(
            summary = "Listar todas las cuentas",
            description = "Devuelve un listado con todas las cuentas registradas en el sistema.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
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
                    description = "Listado de cuentas obtenido correctamente. Puede estar vacío si no hay cuentas registradas.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Account.class))
                    )
            )
    })
    @GetMapping("")
    public ResponseEntity<?> findAccounts() {
        return ResponseEntity.ok(accountService.getAccounts());
    }


    @Operation(
            summary = "Actualizar alias de una cuenta",
            description = "Permite modificar el alias de una cuenta existente usando su ID. El alias debe estar compuesto por tres palabras separadas por puntos, con hasta 10 caracteres por palabra.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID de la cuenta a actualizar.",
                            required = true,
                            in = ParameterIn.PATH,
                            example = "a12f3c4e-5678-90ab-cdef-1234567890ab"
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
                    description = "Objeto con el nuevo alias.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AccountPatchDTO.class),
                            examples = @ExampleObject(value = "{ \"alias\": \"nuevo.alias.cuenta\" }")
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Alias actualizado exitosamente.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Account.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Alias inválido o datos mal formateados.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"El alias debe tener tres palabras separadas por puntos, y cada palabra con un máximo de 10 caracteres.\" }")
                    )
            ),
            @ApiResponse(responseCode = "409", description = "Ya existe una cuenta con ese alias"),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró una cuenta con el ID especificado.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"No existe una cuenta con el ID proporcionado.\" }")
                    )
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAlias(@PathVariable UUID id, @Valid @RequestBody AccountPatchDTO account) {
        return ResponseEntity.ok(accountService.update(id, account));
    }


    @Hidden
    @PostMapping("/deposit/{userId}")
    public ResponseEntity<?> deposit(@PathVariable UUID userId, @Valid @RequestBody ActivityDTO activityDTO){
        accountService.depositMoney(userId, activityDTO);
        return ResponseEntity.ok(200);
    }

    @Hidden
    @PostMapping("/transfer/{userId}")
    public ResponseEntity<?> transfer(@PathVariable UUID userId, @Valid @RequestBody ActivityDTO activityDTO){
        accountService.transfer(userId, activityDTO);
        return ResponseEntity.ok(200);
    }

}
