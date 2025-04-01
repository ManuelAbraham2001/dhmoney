package com.dh.keycloak_service.Repository;

import com.dh.keycloak_service.DTO.UserDTO;
import com.dh.keycloak_service.Entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "users-service", url = "http://localhost:8085/api/users/")
public interface UserFeignClient {
    @PostMapping("/create")
//    @Headers("Content-Type: application/x-www-form-urlencoded")
    UserDTO createUser(@RequestBody User user, @RequestHeader("Authorization") String authorization);
}
