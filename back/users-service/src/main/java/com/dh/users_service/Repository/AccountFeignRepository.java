package com.dh.users_service.Repository;

import com.dh.users_service.DTO.Account;
import com.dh.users_service.DTO.CreateAccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "accounts-service", url = "http://localhost:8085/api/accounts")
public interface AccountFeignRepository {
    @PostMapping("/create/{userId}")
    Account createAccount(@PathVariable UUID userId, @RequestBody CreateAccountDTO createAccountDTO, @RequestHeader String authorization);
}
