package com.dh.cards_service.Repository;

import com.dh.cards_service.Entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    @Query("SELECT c FROM Card c WHERE c.userId = :id")
    List<Card> findCardsByUserId(UUID id);
    Boolean existsByNumber(String number);
}
