package com.rewards.service;

import com.rewards.entity.Transaction;
import com.rewards.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findAllByCustomerIdAndTransactionDateBetween(Long customerId, Timestamp from, Timestamp to){
        return transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customerId, from, to);
    }

    public List<Transaction> findTransactionsOfACustomer(Long customerId, int noOfMonths){
        return transactionRepository.findTransactionsOfACustomer(customerId, noOfMonths);
    }
}
