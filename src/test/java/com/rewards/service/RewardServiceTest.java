package com.rewards.service;

import com.rewards.dto.CustomerRewardPointsDTO;
import com.rewards.entity.Transaction;
import com.rewards.repository.RewardRepository;
import com.rewards.util.RewardsCalculator;
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

public class RewardServiceTest {

    @Mock
    private  RewardRepository rewardRepository;
    @Mock
    private  TransactionService transactionService;
    @Mock
    private  RewardsCalculator rewardsCalculator;
    @InjectMocks
    private RewardService rewardService;

    @BeforeEach
    public void setup(){
        rewardRepository = mock(RewardRepository.class);
        transactionService = mock(TransactionService.class);
        rewardsCalculator = mock(RewardsCalculator.class);

        rewardService = new RewardService(rewardRepository, transactionService, rewardsCalculator);
    }

    @Test
    public void happytest_getRewardPointsForCustomer(){
        List<String> rewardNames= List.of("TestReward");
        Map<Month, Long> pointsByMonth = new HashMap<>();
        pointsByMonth.put(Month.OCTOBER, 40l);
        when(transactionService.findTransactionsOfACustomer(101l, 1)).thenReturn(getTransactionList());
        when(rewardsCalculator.calculatePointsPerMonth(rewardNames, getTransactionsByMonth())).thenReturn(pointsByMonth);

        CustomerRewardPointsDTO rewardPointsForCustomer = rewardService.getRewardPointsForCustomer(101l, rewardNames, 1);

        Assert.assertEquals(40, rewardPointsForCustomer.getRewardsByMonth().values().iterator().next().longValue());
        Assert.assertEquals(40, rewardPointsForCustomer.getTotalRewards());
    }

    @Test
    public void withInvalidReward_getRewardPointsForCustomer(){
        List<String> rewardNames= List.of("TestReward");
        Map<Month, Long> pointsByMonth = new HashMap<>();
        pointsByMonth.put(Month.OCTOBER, 40l);
        when(transactionService.findTransactionsOfACustomer(101l, 1)).thenReturn(getTransactionList());
        when(rewardsCalculator.calculatePointsPerMonth(rewardNames, getTransactionsByMonth())).thenReturn(new HashMap<>());

        CustomerRewardPointsDTO rewardPointsForCustomer = rewardService.getRewardPointsForCustomer(101l, rewardNames, 1);

        Assert.assertEquals(0, rewardPointsForCustomer.getTotalRewards());
    }

    private Map<Month, List<Transaction>> getTransactionsByMonth(){
        Map<Month, List<Transaction>> transactionsByMonth=new HashMap<>();
        transactionsByMonth.put(Month.OCTOBER, getTransactionList());

        return transactionsByMonth;
    }

    private List<Transaction> getTransactionList() {
        Transaction t1= Transaction.builder().customerId(101l).transactionDate(LocalDate.of(2022, 10, 10)).amount(120).build();
        List<Transaction> transactions = List.of(t1);
        return transactions;
    }
}
