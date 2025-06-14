package com.example.spring_boot;

import com.example.spring_boot.entity.Card;
import com.example.spring_boot.repo.CardRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.math.BigDecimal;

@SpringBootTest
public class LockingTest {
    @Autowired
    private CardRepo cardRepo;

    @Test
    public void testLocking(){
        // Create a new card
        Card card = new Card();
        card.setBalance(BigDecimal.valueOf(100));
        cardRepo.saveAndFlush(card);
        Long cardId = cardRepo.findAll().get(0).getId(); //Get all cards --> get first card (index 0) --> Get its ID

        //Simulate 2 users trying to use it at the same time
        Card card1 = cardRepo.findById(cardId).get();
        Card card2 = cardRepo.findById(cardId).get();

        // User 1 updates and saves succesfully
        card1.setBalance(card1.getBalance().add(BigDecimal.valueOf(100)));
        cardRepo.saveAndFlush(card1);

        // User 2 updates and tries to save - Fail
        card2.setBalance(card1.getBalance().add(BigDecimal.valueOf(50)));

        Assertions.assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
            cardRepo.saveAndFlush(card2); // This should fail due to version mismatch
        });
    }
}
