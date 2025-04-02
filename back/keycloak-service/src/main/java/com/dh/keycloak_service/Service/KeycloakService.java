package com.dh.keycloak_service.Service;

import com.dh.keycloak_service.DTO.Login;
import com.dh.keycloak_service.DTO.UserDTO;
import com.dh.keycloak_service.Entity.User;
import com.dh.keycloak_service.Exception.IncorrectPasswordException;
import com.dh.keycloak_service.Exception.UserAlreadyExistsException;
import com.dh.keycloak_service.Exception.UserNotFoundException;
import com.dh.keycloak_service.Repository.KeycloakFeignClient;
import com.dh.keycloak_service.Repository.UserFeignClient;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class KeycloakService {

    @Value("{REALM}")
    private String realm;

    @Value("{CLIENT_ID}")
    private String clientId;

    @Value("{CLIENT_SECRET}")
    private String clientSecret;

    @Value("{ADMIN_USERNAME}")
    private String adminUsername;

    @Value("{ADMIN_PASSWORD}")
    private String adminPassword;

    private final KeycloakFeignClient keycloakFeignClient;
    private final UserFeignClient userFeignClient;

    public KeycloakService(KeycloakFeignClient keycloakFeignClient, UserFeignClient userFeignClient) {
        this.keycloakFeignClient = keycloakFeignClient;
        this.userFeignClient = userFeignClient;
    }

    public Keycloak createKeycloakClient() {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080")
                .realm(realm)
                .username(adminUsername)
                .password(adminPassword)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType("password")
                .build();
    }


    public UserDTO registerUser(User user) {

        Keycloak keycloak = createKeycloakClient();

        List<UserRepresentation> users = keycloak.realm("dh")
                .users()
                .searchByEmail(user.getEmail(), true);

        if (!users.isEmpty()) {
            throw new UserAlreadyExistsException(user.getEmail());
        }

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(user.getPassword());
        credential.setTemporary(false);

        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setUsername(user.getFirstName() + user.getLastName() + LocalDate.now());
        keycloakUser.setEmail(user.getEmail());
        keycloakUser.setEnabled(true);
        keycloakUser.setFirstName(user.getFirstName());
        keycloakUser.setLastName(user.getLastName());
        keycloakUser.setCredentials(new ArrayList<>());
        keycloakUser.getCredentials().add(credential);

        Response keycloakUserDB = keycloak.realm("dh").users().create(keycloakUser);

        String locationHeader = keycloakUserDB.getHeaderString("Location");
        String userId = locationHeader.substring(locationHeader.lastIndexOf("/") + 1);

        user.setId(UUID.fromString(userId));

        Map<String, String> token = login(new Login(user.getEmail(), user.getPassword()));

        UserDTO userDTO = userFeignClient.createUser(user, "Bearer " + token.get("token"));

        userDTO.setAccessToken(token.get("token"));
        return userDTO;
    }


    private static final String JWKS_URL = "http://localhost:8080/realms/dh/protocol/openid-connect/certs";

    public static String extractUserIdFromToken(String token) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(token);

        String kid = signedJWT.getHeader().getKeyID();

        JWKSet jwkSet = JWKSet.load(new URL(JWKS_URL));

        JWK jwk = jwkSet.getKeys().stream()
                .filter(k -> kid.equals(k.getKeyID()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró la clave con kid: " + kid));

        RSAKey rsaKey = (RSAKey) jwk;
        RSAPublicKey publicKey = rsaKey.toRSAPublicKey();

        JWSVerifier verifier = new RSASSAVerifier(publicKey);

        if (!signedJWT.verify(verifier)) {
            throw new SecurityException("Firma del token inválida");
        }

        return signedJWT.getJWTClaimsSet().getSubject();
    }

    public Map<String, String> login(Login user){
        try {
            Keycloak adminKeycloak = createKeycloakClient();

            List<UserRepresentation> users = adminKeycloak.realm("dh")
                    .users()
                    .searchByEmail(user.getEmail(), true);

            if (users.isEmpty()) {
                throw new UserNotFoundException("Usuario no encontrado");
            }

            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl("http://localhost:8080")
                    .realm(realm)
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .grantType("password")
                    .build();

            AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();

            return Map.of("token", tokenResponse.getToken());

        } catch (WebApplicationException e) {
            if (e.getResponse().getStatus() == 401) {
                throw new IncorrectPasswordException("Contraseña incorrecta");
            }
        }
        return null;
    }

    public void logout(String token) throws Exception {
        String userId = extractUserIdFromToken(token);
        Keycloak keycloak = createKeycloakClient();

        keycloak.realm("dh")
                .users()
                .get(userId)
                .logout();
    }

}
