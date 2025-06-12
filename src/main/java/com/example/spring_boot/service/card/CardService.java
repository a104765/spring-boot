package com.example.spring_boot.service.card;

import com.example.spring_boot.entity.Card;
import com.example.spring_boot.entity.Transaction;

import java.util.List;

public interface CardService {
    Card saveCard(Card card);

    Card getCard(Long id);

    List<Card> fetchCardList();

    Card updateCard(Card card, Long id);

    Card transactionCard(Long id, Transaction transaction);
}
