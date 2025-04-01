package com.dh.accounts_service.Service;

import com.dh.accounts_service.DTO.AccountPatchDTO;
import com.dh.accounts_service.DTO.ActivityDTO;
import com.dh.accounts_service.DTO.CreateAccountDTO;
import com.dh.accounts_service.Entity.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAccountService {
    public Account create(UUID userId, CreateAccountDTO account);
    public Account update(UUID id, AccountPatchDTO account);
    public Optional<Account> getAccountById(UUID id);
    public List<Account> getAccounts();
    public Account getAccountByCvuOrAlias(String cvuOrAlias);
    public void depositMoney(UUID id, ActivityDTO activity);
    public void transfer(UUID id, ActivityDTO activity);
}
