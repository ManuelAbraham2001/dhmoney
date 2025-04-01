package com.dh.cards_service.Service;

import com.dh.cards_service.DTO.CardDTO;
import com.dh.cards_service.DTO.ListCardDTO;

import java.util.List;
import java.util.UUID;

public interface ICardService {
    CardDTO create(CardDTO card, UUID id);
    List<ListCardDTO> listCards(UUID id);
    void deleteCard(UUID cardId);
}
