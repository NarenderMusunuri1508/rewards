package com.rewards.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewards.dto.CustomerRewardPointsDTO;
import com.rewards.dto.CustomerRewardsRequest;
import com.rewards.repository.RewardRepository;
import com.rewards.repository.TransactionRepository;
import com.rewards.service.RewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({CustomerRewardsController.class})
public class CustomerRewardsControllerTest {
    @MockBean
    private RewardService rewardService;
    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private RewardRepository rewardRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void happyTest_getRewardPointsForCustomer_ReturnsOK() throws Exception{
        CustomerRewardsRequest request = CustomerRewardsRequest.builder().rewardNames(List.of("TestReward")).rewardsForNoOfMonths(1).build();
        Map<Month, Long> pointsByMonth = new HashMap<>();
        pointsByMonth.put(Month.OCTOBER, 40l);
        CustomerRewardPointsDTO response = CustomerRewardPointsDTO.builder().customerId(101l).rewardsByMonth(pointsByMonth).totalRewards(40l).build();

        when(rewardService.getRewardPointsForCustomer(101l, request.getRewardNames(), request.getRewardsForNoOfMonths())).thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/customer/101/rewards")
                                .content(asJsonString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"error\":null,\"data\":{\"customerId\":101,\"rewardsByMonth\":{\"OCTOBER\":40},\"totalRewards\":40}}"));;
    }

    @Test
    public void withNoRewards_getRewardPointsForCustomer_Returns400() throws Exception{
        CustomerRewardsRequest request = CustomerRewardsRequest.builder().rewardNames(null).rewardsForNoOfMonths(1).build();
        Map<Month, Long> pointsByMonth = new HashMap<>();
        pointsByMonth.put(Month.OCTOBER, 40l);
        CustomerRewardPointsDTO response = CustomerRewardPointsDTO.builder().customerId(101l).rewardsByMonth(pointsByMonth).totalRewards(40l).build();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/customer/101/rewards")
                                .content(asJsonString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
