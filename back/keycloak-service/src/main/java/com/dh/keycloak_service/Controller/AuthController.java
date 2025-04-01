package com.dh.keycloak_service.Controller;

import com.dh.keycloak_service.DTO.Login;
import com.dh.keycloak_service.DTO.UserDTO;
import com.dh.keycloak_service.Entity.User;
import com.dh.keycloak_service.Service.KeycloakService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin("*")
@Tag(name = "Autorizacion", description = "Registro, Login y Logout")
public class AuthController {

    @Autowired
    private KeycloakService keycloakService;

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @PostMapping("/login")
    @Operation(
            summary = "Login",
            description = "Autentica al usuario"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login exitoso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{ \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\" }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "El usuario no existe", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"error\": \"Usuario no encontrado\"}")
            )),
            @ApiResponse(responseCode = "400", description = "La contraseña es incorrecta", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"error\": \"Contraseña incorrecta\"}")
            ))
        }
    )
    public ResponseEntity<?> login(@Valid @RequestBody Login user) {
        Map<String, String> token = keycloakService.login(user);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register",
            description = "Registro de usuario"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuario creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Campos incompletos/invalidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"campo\": \"El campo es obligatorio/invalido\" }")
            )),
            @ApiResponse(responseCode = "409", description = "Ya existe una cuenta con ese email", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"error\": \"Ya existe un usuario con el email: {email}\" }")
            ))
    }
    )
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        UserDTO userDTO = keycloakService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PostMapping("/logout")
    @Operation(
            summary = "Cerrar sesión del usuario",
            description = "Finaliza la sesión del usuario usando el token JWT enviado en el header Authorization"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout exitoso"),
            @ApiResponse(responseCode = "401", description = "Token inválido o no autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> logout(@Parameter(description = "Token JWT en formato Bearer", required = true, example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    ) @RequestHeader("Authorization") String authHeader) throws Exception {
        String token = authHeader.substring(7);
        keycloakService.logout(token);
        return ResponseEntity.ok().build();
    }

}
