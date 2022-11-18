package com.rewards.util;

import com.rewards.entity.Reward;
import com.rewards.entity.Transaction;
import com.rewards.repository.RewardRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RewardsCalculatorTest {

    @Mock
    private RewardRepository rewardRepository;
    @InjectMocks
    private RewardsCalculator rewardsCalculator;

    @BeforeEach
    public void setUp(){
        rewardRepository = mock(RewardRepository.class);
        rewardsCalculator = new RewardsCalculator(rewardRepository);
    }

    @Test
    public void happyTest_CalculatePointsPerMonth(){
        List<String> rewardNames= List.of("TestReward");
        Reward reward = Reward.builder().name("TestReward").points(2).minTransactionAmount(50).build();

        when(rewardRepository.getRewardByName("TestReward")).thenReturn(reward);

        Map<Month, Long> pointsByMonth = rewardsCalculator.calculatePointsPerMonth(rewardNames, getTransactions());

        Assert.assertEquals(140, pointsByMonth.values().iterator().next().longValue());
    }

    @Test
    public void withInvalidReward_CalculatePointsPerMonth(){
        List<String> rewardNames= List.of("TestReward");

        when(rewardRepository.getRewardByName("TestReward")).thenReturn(null);

        Map<Month, Long> pointsByMonth = rewardsCalculator.calculatePointsPerMonth(rewardNames, getTransactions());

        Assert.assertTrue(pointsByMonth.keySet().size() == 0);
    }

    @Test
    public void withNoTransactions_CalculatePointsPerMonth(){
        List<String> rewardNames= List.of("TestReward");
        Reward reward = Reward.builder().name("TestReward").points(2).minTransactionAmount(50).build();

        when(rewardRepository.getRewardByName("TestReward")).thenReturn(reward);

        Map<Month, Long> pointsByMonth = rewardsCalculator.calculatePointsPerMonth(rewardNames, new HashMap<>());

        Assert.assertTrue(pointsByMonth.keySet().size() == 0);
    }

    private Map<Month, List<Transaction>>  getTransactions(){
        Map<Month, List<Transaction>> transactionsByMonth=new HashMap<>();
        Transaction t1= Transaction.builder().customerId(101l).transactionDate(LocalDate.of(2022, 10, 10)).amount(120).build();
        List<Transaction> transactions = List.of(t1);

        transactionsByMonth.put(Month.OCTOBER, transactions);

        return transactionsByMonth;
    }
}
