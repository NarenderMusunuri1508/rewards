package com.rewards.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionDTO {

    private Long id;

    private Long customerId;

    private Timestamp transactionDate;

    private Double amount;

    private Long productId;

}
