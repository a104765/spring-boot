package com.example.spring_boot;

import com.example.spring_boot.entity.Card;
import com.example.spring_boot.entity.Transaction;
import com.example.spring_boot.repo.CardRepo;
import com.example.spring_boot.service.card.CardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class CardOperations {

    @Autowired
    private CardRepo cardRepo;

    @Autowired
    private CardServiceImpl cardService;

    @Test
    public void testCardCreate(){
        Card card = new Card();
        card.setBalance(BigDecimal.valueOf(100));
        card.setCardholderName("Alice");
        cardService.saveCard(card);
        Card dbCard = cardRepo.findAll().get(0);

        Assertions.assertEquals(card.getId(), dbCard.getId());
        Assertions.assertEquals(0, card.getBalance().compareTo(dbCard.getBalance()));
        Assertions.assertEquals(card.getVersion(), dbCard.getVersion());
    }

    @Test
    public void testCardSpend(){
        BigDecimal originalAmount = BigDecimal.valueOf(100);
        BigDecimal spendAmount = BigDecimal.valueOf(90);
        Card card = new Card();
        card.setBalance(originalAmount);
        card.setCardholderName("Alice");
        cardService.saveCard(card);
        Card dbCard = cardRepo.findAll().get(0);

        Transaction transaction = new Transaction();
        transaction.setType("spend");
        transaction.setCard(dbCard);
        transaction.setAmount(spendAmount);
        cardService.transactionCard(dbCard.getId(),transaction);
        BigDecimal amountAfter = dbCard.getBalance();

        Assertions.assertEquals(0, dbCard.getBalance().compareTo(amountAfter));

    }

    @Test
    public void testCardTopup(){
        BigDecimal originalAmount = BigDecimal.valueOf(100);
        BigDecimal spendAmount = BigDecimal.valueOf(90);
        Card card = new Card();
        card.setBalance(originalAmount);
        card.setCardholderName("Alice");
        cardService.saveCard(card);
        Card dbCard = cardRepo.findAll().get(0);

        Transaction transaction = new Transaction();
        transaction.setType("topup");
        transaction.setCard(dbCard);
        transaction.setAmount(spendAmount);
        cardService.transactionCard(dbCard.getId(),transaction);
        BigDecimal amountAfter = dbCard.getBalance();

        Assertions.assertEquals(0, dbCard.getBalance().compareTo(amountAfter));
    }
}
