package com.example.spring_boot.service.transaction;

import com.example.spring_boot.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction commitTransaction(Transaction transaction);

    Transaction getTransaction(Long id);

    List<Transaction> fetchTransactionsByCardId(Long id);
}
