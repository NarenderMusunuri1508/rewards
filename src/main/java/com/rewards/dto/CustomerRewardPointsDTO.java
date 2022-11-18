package com.rewards.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRewardPointsDTO {
    private long customerId;
    private Map<Month, Long> rewardsByMonth;
    private long totalRewards;
}
