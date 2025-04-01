package com.dh.keycloak_service.Repository;

import com.dh.keycloak_service.DTO.KeycloakTokenResponse;
import com.dh.keycloak_service.DTO.KeycloakUserRequest;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "keycloak-auth", url = "http://localhost:8080")
public interface KeycloakFeignClient  {

//    @PostMapping(value = "/realms/dh/protocol/openid-connect/token",
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    @Headers("Content-Type: application/x-www-form-urlencoded")
//    KeycloakTokenResponse getAdminToken(@RequestBody MultiValueMap<String, String> formParams);

    @PostMapping(value = "/realms/dh/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Headers("Content-Type: application/x-www-form-urlencoded")
    KeycloakTokenResponse login(@RequestBody MultiValueMap<String, String> formParams);

//    @PostMapping(value = "/admin/realms/{realm}/users", consumes = "application/json")
//    ResponseEntity<Void> createUser(
//            @PathVariable("realm") String realm,
//            @RequestHeader("Authorization") String token,
//            @RequestBody KeycloakUserRequest userRequest
//    );

}
