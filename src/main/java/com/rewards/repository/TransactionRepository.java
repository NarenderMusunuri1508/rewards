package com.rewards.repository;

import com.rewards.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

     List<Transaction> findAllByCustomerId(Long customerId);

     List<Transaction> findAllByCustomerIdAndTransactionDateBetween(Long customerId, Timestamp from, Timestamp to);

     @Query("From Transaction WHERE CUSTOMER_ID= :customerId AND TRANSACTION_DATE >= DATEADD(MONTH, -:noOfMonths, CURRENT_DATE())")
     List<Transaction> findTransactionsOfACustomer(Long customerId, int noOfMonths);
}
