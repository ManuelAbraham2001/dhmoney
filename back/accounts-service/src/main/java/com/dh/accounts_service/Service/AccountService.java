package com.dh.accounts_service.Service;

import com.dh.accounts_service.DTO.AccountPatchDTO;
import com.dh.accounts_service.DTO.ActivityDTO;
import com.dh.accounts_service.DTO.CreateAccountDTO;
import com.dh.accounts_service.Entity.Account;
import com.dh.accounts_service.Exception.AccountNotFoundException;
import com.dh.accounts_service.Exception.AliasAlreadyExists;
import com.dh.accounts_service.Exception.TransferDeniedException;
import com.dh.accounts_service.Repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService{

    @Autowired
    private AccountRepository accountRepository;

    private static final Random RANDOM = new Random();

    private String generateCvu() {
        StringBuilder cvu = new StringBuilder();
        for (int i = 0; i < 22; i++) {
            cvu.append(RANDOM.nextInt(10));
        }
        return cvu.toString();
    }

    private String generateAlias() {
        int ALIAS_LENGTH = 3;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dictionary.txt");
        try {
            assert inputStream != null;
            List<String> words = new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .toList();

            if (words.size() < ALIAS_LENGTH) {
                throw new IllegalArgumentException("No hay suficientes palabras en el archivo.");
            }

            StringBuilder alias = new StringBuilder();
            for (int i = 0; i < ALIAS_LENGTH; i++) {
                String word = words.get(RANDOM.nextInt(words.size()));
                alias.append(word);
                if (i < ALIAS_LENGTH - 1) {
                    alias.append(".");
                }
            }

            return alias.toString();

        } catch (RuntimeException e) {
            throw new RuntimeException("Error al leer el archivo de palabras", e);
        }
    }

    private String generateUniqueAlias() {
        String alias = generateAlias();
        while (accountRepository.findByAlias(alias) != null) {
            alias = generateAlias();
        }
        return alias;
    }

    @Override
    public Account create(UUID userId, CreateAccountDTO accountDTO) {
        String name = accountDTO.getFirstName() + " " + accountDTO.getLastName();
        String cvu = generateCvu();
        String alias = generateUniqueAlias();
        Account account = new Account(null, userId, name, cvu, alias, 0.0);
        return accountRepository.save(account);
    }

    @Override
    public Account update(UUID id, AccountPatchDTO accountPatchDTO) {
        Account account = accountRepository.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        String newAlias = accountPatchDTO.getAlias();

        if (newAlias != null && !newAlias.isBlank()) {
            String aliasExistente = accountRepository.findByAlias(newAlias);

            if (aliasExistente != null && !aliasExistente.equals(account.getAlias())) {
                throw new AliasAlreadyExists("El alias ya está en uso por otra cuenta");
            }

            account.setAlias(newAlias);
            return accountRepository.save(account);
        }

        return null;
    }


    @Override
    public Optional<Account> getAccountById(UUID id) {
        return accountRepository.findByUserId(id);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountByCvuOrAlias(String cvuOrAlias) {
        Optional<Account> account = accountRepository.findByCvuOrAlias(cvuOrAlias);
        if(account.isEmpty()){
            throw new AccountNotFoundException(cvuOrAlias);
        }
        return account.get();
    }

    @Override
    public void depositMoney(UUID userId, ActivityDTO activity) {
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new TransferDeniedException("No se encontró una cuenta para el usuario: " + userId, HttpStatus.BAD_REQUEST));

        double amount = activity.getAmount();
        if (amount <= 0) {
            throw new TransferDeniedException("El monto debe ser mayor a 0", HttpStatus.BAD_REQUEST);
        }

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void transfer(UUID userId, ActivityDTO activity) {
        Account origin = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new TransferDeniedException("No se encontró una cuenta para el usuario: " + userId, HttpStatus.BAD_REQUEST));

        validateTransferOwnership(origin, activity.getOrigin());

        Account destination = accountRepository.findByCvuOrAlias(activity.getDestination())
                .orElseThrow(() -> new TransferDeniedException("La cuenta de destino no existe", HttpStatus.BAD_REQUEST));

        validateDifferentAccounts(origin, destination);

        double amount = activity.getAmount();
        validateNegativeTransferAmount(amount, origin.getBalance());

        origin.setBalance(origin.getBalance() + amount);
        destination.setBalance(destination.getBalance() - amount); // negativo -(-500) = suma

        accountRepository.save(origin);
        accountRepository.save(destination);
    }


    private void validateTransferOwnership(Account account, String origin) {
        if (!origin.equals(account.getCvu()) && !origin.equals(account.getAlias())) {
            throw new TransferDeniedException("No puedes transferir desde una cuenta que no te pertenece", HttpStatus.FORBIDDEN);
        }
    }

    private void validateDifferentAccounts(Account origin, Account destination) {
        if (origin.getId().equals(destination.getId())) {
            throw new TransferDeniedException("La cuenta de origen y destino no pueden ser la misma", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateNegativeTransferAmount(double amount, double balance) {
        if (amount >= 0) {
            throw new TransferDeniedException("El monto de la transferencia debe ser negativo", HttpStatus.BAD_REQUEST);
        }

        double absAmount = Math.abs(amount);
        if (balance < absAmount) {
            throw new TransferDeniedException("Fondos insuficientes", HttpStatus.BAD_REQUEST);
        }
    }



}
