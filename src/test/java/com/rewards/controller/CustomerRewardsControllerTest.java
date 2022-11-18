package com.rewards.controller;

import com.rewards.dto.CustomerRewardPointsDTO;
import com.rewards.dto.CustomerRewardsRequest;
import com.rewards.repository.RewardRepository;
import com.rewards.service.RewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = CustomerRewardsController.class)
@ComponentScan("com.rewards.*")
public class CustomerRewardsControllerTest {
    @InjectMocks
    private RewardService rewardService;


    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        rewardService = mock(RewardService.class);
    }

    @Test
    public void happyTest_getRewardPointsForCustomer() throws Exception{
        CustomerRewardsRequest request = CustomerRewardsRequest.builder().rewardNames(List.of("TestReward")).rewardsForNoOfMonths(1).build();
        Map<Month, Long> pointsByMonth = new HashMap<>();
        pointsByMonth.put(Month.OCTOBER, 40l);
        CustomerRewardPointsDTO response = CustomerRewardPointsDTO.builder().customerId(101l).rewardsByMonth(pointsByMonth).totalRewards(40l).build();

        when(rewardService.getRewardPointsForCustomer(101l, request.getRewardNames(), request.getRewardsForNoOfMonths())).thenReturn(response);


        this.mockMvc.perform(post("/customer/101/rewards")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
