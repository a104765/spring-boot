package com.example.spring_boot.service.card;

import com.example.spring_boot.entity.Card;
import com.example.spring_boot.entity.Transaction;
import com.example.spring_boot.repo.CardRepo;
import com.example.spring_boot.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepo cardRepo; // Inits the Card Repo into this class. Same as .... new CardRepo()

    @Autowired
    private TransactionService transactionService;

    @Override
    public Card saveCard(Card card) {
        card.setCreatedAt(LocalDateTime.now());
        return cardRepo.save(card);
    }

    @Override
    public Card getCard(Long id) {
        return cardRepo.findById(id).get();
    }

    @Override
    public List<Card> fetchCardList() {
        return (List<Card>) cardRepo.findAll();
    }

    @Override
    public Card updateCard(Card card, Long id) {
        Card cardDB = cardRepo.findById(id).get();
        cardDB.setBalance(card.getBalance());
        cardDB.setCardholderName(card.getCardholderName());
        return cardRepo.save(cardDB);
    }

    @Override
    public Card transactionCard(Long id, Transaction transaction) {
        Card cardDB = cardRepo.findById(id).get();
        if (transaction.getType().equals("spend")){
            if ((cardDB.getBalance().subtract(transaction.getAmount())).compareTo(BigDecimal.ZERO)>=0){
                transaction.setCard(cardDB);
                transaction.setType("spend");
                transactionService.commitTransaction(transaction);
                cardDB.setBalance(cardDB.getBalance().subtract(transaction.getAmount()));
            }
            else{
                return cardDB;
            }
        }
        else if (transaction.getType().equals("topup")){
            transaction.setCard(cardDB);
            transaction.setType("topup");
            transactionService.commitTransaction(transaction);
            cardDB.setBalance(cardDB.getBalance().add(transaction.getAmount()));
        }

        return cardRepo.save(cardDB);
    }
}
