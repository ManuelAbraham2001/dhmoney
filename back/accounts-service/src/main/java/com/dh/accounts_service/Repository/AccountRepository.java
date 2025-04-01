package com.dh.accounts_service.Repository;

import com.dh.accounts_service.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByUserId(UUID id);
    Optional<Account> findByCvu(String cvu);
    @Query("SELECT a FROM Account a WHERE a.alias = :value OR a.cvu = :value")
    Optional<Account> findByCvuOrAlias(@Param("value") String cvuOrAlias);
    @Query("SELECT a.alias FROM Account a WHERE a.alias = :alias")
    String findByAlias(String alias);
}
