package com.example.spring_boot.service.transaction;

import com.example.spring_boot.entity.Transaction;
import com.example.spring_boot.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepo transactionRepo;

    @Override
    public Transaction commitTransaction(Transaction transaction) {
        transaction.setTimestamp(LocalDateTime.now());
        return transactionRepo.save(transaction);
    }

    @Override
    public Transaction getTransaction(Long id) {
        return transactionRepo.findById(id).get();
    }

    @Override
    public List<Transaction> fetchTransactionsByCardId(Long id) {
        return (List<Transaction>) transactionRepo.findByCardId(id);
    }
}
