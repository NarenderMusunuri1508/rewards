package com.rewards.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RewardDTO {

    private Long id;

    private String name;

    private String description;

    private int points;

    private int minTransactionAmount;
}
