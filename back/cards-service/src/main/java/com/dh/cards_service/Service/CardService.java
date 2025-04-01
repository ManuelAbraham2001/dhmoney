package com.dh.cards_service.Service;

import com.dh.cards_service.DTO.CardDTO;
import com.dh.cards_service.DTO.ListCardDTO;
import com.dh.cards_service.Entity.Card;
import com.dh.cards_service.Exception.CardAlreadyExistsException;
import com.dh.cards_service.Exception.CardNotFoundException;
import com.dh.cards_service.Repository.CardRepository;
import jakarta.ws.rs.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CardService implements ICardService{

    @Autowired
    private CardRepository cardRepository;

    @Override
    public CardDTO create(CardDTO cardDTO, UUID id) {
        if(cardRepository.existsByNumber(cardDTO.getNumber())){
            throw new CardAlreadyExistsException(cardDTO.getNumber());
        }
        Card card = new Card(null, id, cardDTO.getExpiration(), cardDTO.getName(), cardDTO.getNumber(), cardDTO.getCvc());
        cardRepository.save(card);
        cardDTO.setId(card.getId());
        return cardDTO;
    }

    @Override
    public List<ListCardDTO> listCards(UUID id) {
        List<Card> cards = cardRepository.findCardsByUserId(id);
        List<ListCardDTO> cardsDTO = new ArrayList<>();
        for(Card c : cards){
            cardsDTO.add(new ListCardDTO(c.getId(), c.getName(), c.getNumber(), c.getExpiration()));
        }
        return cardsDTO;
    }

    @Override
    public void deleteCard(UUID cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));

        cardRepository.delete(card);
    }

}
