package com.example.spring_boot.service.card;

import com.example.spring_boot.entity.Card;
import com.example.spring_boot.entity.Transaction;
import com.example.spring_boot.exception.BadRequestException;
import com.example.spring_boot.exception.ConflictException;
import com.example.spring_boot.repo.CardRepo;
import com.example.spring_boot.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepo cardRepo; // Inits the Card Repo into this class. Same as .... new CardRepo()

    @Autowired
    private TransactionService transactionService;

    @Override
    public Card saveCard(Card card) {
        return cardRepo.save(card);
    }

    @Override
    public Card getCard(Long id) {
        return cardRepo.findById(id)
                .orElseThrow(() -> new BadRequestException("Card with ID: " + id + " not found"));
    }

    @Override
    public List<Card> fetchCardList() {
        return (List<Card>) cardRepo.findAll();
    }

    @Override
    public Card updateCard(Card card, Long id) {
        Card cardDB = cardRepo.findById(id)
                .orElseThrow(() -> new BadRequestException("Card with ID: " + id + " not found"));
        cardDB.setBalance(card.getBalance());
        cardDB.setCardholderName(card.getCardholderName());
        return cardRepo.save(cardDB);
    }

    @Override
    public Card transactionCard(Long id, Transaction transaction) {
        Card cardDB = cardRepo.findById(id)
                .orElseThrow(() -> new BadRequestException("Card with ID: " + id + " not found"));
        if (transaction.getType().equals("spend")){
            if ((cardDB.getBalance().subtract(transaction.getAmount())).compareTo(BigDecimal.ZERO)>=0){ //Checks if there are enough funds by subtracting the original amount by the transaction amount
                transaction.setCard(cardDB);
                transaction.setType("spend");
                transactionService.commitTransaction(transaction);
                cardDB.setBalance(cardDB.getBalance().subtract(transaction.getAmount()));
            }
            else{
                throw new ConflictException("Not enough funds");
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
