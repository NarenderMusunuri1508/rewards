package com.rewards.service;

import com.rewards.dto.CustomerRewardPointsDTO;
import com.rewards.dto.RewardDTO;
import com.rewards.entity.Reward;
import com.rewards.entity.Transaction;
import com.rewards.repository.RewardRepository;
import com.rewards.util.RewardsCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RewardService {

    @Autowired
    private final RewardRepository rewardRepository;
    @Autowired
    private final TransactionService transactionService;
    @Autowired
    private final RewardsCalculator rewardsCalculator;

    Logger logger = LoggerFactory.getLogger(RewardService.class);

    public RewardService(RewardRepository rewardRepository, TransactionService transactionService, RewardsCalculator rewardsCalculator) {
        this.rewardRepository = rewardRepository;
        this.transactionService = transactionService;
        this.rewardsCalculator = rewardsCalculator;
    }

    public RewardDTO getReward(String rewardName){
        Reward reward = rewardRepository.getRewardByName(rewardName);
        return RewardDTO.builder()
                .id(reward.getId())
                .name(reward.getName())
                .description(reward.getDescription())
                .minTransactionAmount(reward.getMinTransactionAmount()).build();
    }

    public CustomerRewardPointsDTO getRewardPointsForCustomer(Long id, List<String> rewardNames, int noOfMonths){
        List<Transaction> transactions = transactionService.findTransactionsOfACustomer(id, noOfMonths);
        CustomerRewardPointsDTO customerRewardPointsDTO = new CustomerRewardPointsDTO();

        if(CollectionUtils.isEmpty(transactions)){
            customerRewardPointsDTO.setCustomerId(id);
            return customerRewardPointsDTO;
        }

        Map<Month, List<Transaction>> transactionsByMonth = getTransactionsByMonth(transactions);

        Map<Month, Long> pointsPerMonth = rewardsCalculator.calculatePointsPerMonth(rewardNames, transactionsByMonth);

        Optional<Long> totalPointsForCustomer = pointsPerMonth.values().stream().reduce(Long::sum);

        if(!totalPointsForCustomer.isPresent()) return new CustomerRewardPointsDTO();

        return CustomerRewardPointsDTO.builder()
                .customerId(id)
                .rewardsByMonth(pointsPerMonth)
                .totalRewards(totalPointsForCustomer.get()).build();
    }

    private Map<Month, List<Transaction>> getTransactionsByMonth(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getTransactionDate().getMonth()));
    }
}
