package com.example.spring_boot.controller;

import com.example.spring_boot.dto.cardFundsChange;
import com.example.spring_boot.dto.cardCreate;
import com.example.spring_boot.entity.Card;
import com.example.spring_boot.entity.Transaction;
import com.example.spring_boot.service.card.CardService;
import com.example.spring_boot.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CardController {
    @Autowired
    private CardService cardService;

    @Autowired
    private TransactionService transactionService;


    @PostMapping("/cards")
    public Card saveCard(@Valid @RequestBody cardCreate cardDTO){
        Card card = new Card();
        card.setCardholderName(cardDTO.getCardholderName());
        card.setBalance(cardDTO.getInitialBalance());
        return cardService.saveCard(card);
    }

    @GetMapping("/cards")
    public List<Card> fetchCardList() {
        return cardService.fetchCardList();
    }

    @GetMapping("/cards/{id}")
    public Card getCard(@PathVariable("id") Long cardId) {
        return cardService.getCard(cardId);
    }

    @PostMapping("/cards/{id}/spend")
    public ResponseEntity<cardFundsChange> spend(@PathVariable("id") Long cardId, @RequestBody Transaction transaction){
        transaction.setType("spend");
        BigDecimal orgAmount = cardService.getCard(cardId).getBalance();
        Card card = cardService.transactionCard(cardId, transaction);

        if (orgAmount.compareTo(card.getBalance()) > 0) {
            //Checks if card has new balance. If yes, it means that spend was ok. If not, it means that there are no funds.
            cardFundsChange response = new cardFundsChange(card.getId(), card.getBalance());
            return ResponseEntity.ok(response);
        }
        else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

    }

    @PostMapping("/cards/{id}/topup")
    public ResponseEntity<cardFundsChange> topup(@PathVariable("id") Long cardId, @RequestBody Transaction transaction){
        transaction.setType("topup");
        Card card = cardService.transactionCard(cardId, transaction);
        cardFundsChange response = new cardFundsChange(card.getId(), card.getBalance());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cards/{id}/transactions")
    public List<Transaction> fetchCardTransactions(@PathVariable("id") Long cardId){
        return transactionService.fetchTransactionsByCardId(cardId);
    }

}
