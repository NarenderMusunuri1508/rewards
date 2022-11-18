package com.rewards.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "REWARD")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reward {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "REWARD_ID")
    private long id;

    @Column(name = "REWARD_NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "REWARD_POINTS")
    private int points;

    @Column(name = "MIN_TRANSACTION_AMOUNT")
    private int minTransactionAmount;

}
