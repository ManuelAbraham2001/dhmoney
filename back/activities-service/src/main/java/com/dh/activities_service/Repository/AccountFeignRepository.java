package com.dh.activities_service.Repository;

import com.dh.activities_service.DTO.ActivityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "accounts-service", url = "http://localhost:8085/api/accounts")
public interface AccountFeignRepository {
    @PostMapping("/deposit/{id}")
    ResponseEntity<?> deposit(@PathVariable UUID id, @RequestBody ActivityDTO activityDTO, @RequestHeader("Authorization") String token);
    @PostMapping("/transfer/{id}")
    ResponseEntity<?> transfer(@PathVariable UUID id, @RequestBody ActivityDTO activityDTO, @RequestHeader("Authorization") String token);
}
