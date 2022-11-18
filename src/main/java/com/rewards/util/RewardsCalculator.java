package com.rewards.util;

import com.rewards.entity.Reward;
import com.rewards.entity.Transaction;
import com.rewards.repository.RewardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class RewardsCalculator {
    @Autowired
    private final RewardRepository rewardRepository;
    Logger logger = LoggerFactory.getLogger(RewardsCalculator.class);

    public RewardsCalculator(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    public Map<Month, Long> calculatePointsPerMonth(List<String> rewardNames, Map<Month, List<Transaction>> transactionsByMonth) {
        Map<Month, Long> pointsByMonth = new HashMap<>();
        for(String rewardName: rewardNames) {
            Reward reward = rewardRepository.getRewardByName(rewardName);
            if(Objects.isNull(reward)){
                logger.info("no reward found with name "+ rewardName);
                continue;
            }
            for (Month month : transactionsByMonth.keySet()) {
                pointsByMonth.putIfAbsent(month, 0l);
                Long pointsForAMonth = transactionsByMonth.get(month).stream()
                        .map(t -> calculateRewards(t, reward))
                        .collect(Collectors.summingLong(r -> r.longValue()));

                pointsByMonth.put(month, pointsByMonth.get(month)+pointsForAMonth);
            }
        }
        return pointsByMonth;
    }

    public Long calculateRewards(Transaction transaction, Reward reward) {
        if (transaction.getAmount() > reward.getMinTransactionAmount()) {
            return Math.round(transaction.getAmount() - reward.getMinTransactionAmount()) * reward.getPoints();
        }

        return 0l;
    }
}
