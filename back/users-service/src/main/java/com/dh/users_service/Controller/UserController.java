package com.dh.users_service.Controller;

import com.dh.users_service.DTO.UserAccountDTO;
import com.dh.users_service.DTO.UserDTO;
import com.dh.users_service.Entity.User;
import com.dh.users_service.Service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
//@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;

    @Hidden
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody User user, @RequestHeader("Authorization") String authorization){
        UserAccountDTO userDTO = userService.create(user, authorization);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener un Usuario",
            description = "Devuelve un usuario por su ID",
            parameters = {
                    @Parameter(
                            name = "Authorization",
                            description = "Token JWT en formato Bearer",
                            required = true,
                            in = ParameterIn.HEADER,
                            example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                    )
            }
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "ID del usuario no es un UUID"),
            @ApiResponse(responseCode = "404", description = "No se encontro el usuario", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"error\": \"No existe un usuario con el id: {id}\" }")
            ))
    }
    )
    public ResponseEntity<?> findByEmail(@PathVariable UUID id){
        return ResponseEntity.ok(userService.findById(id));
    }
}
