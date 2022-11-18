package com.rewards.controller;

import com.rewards.dto.CustomerRewardPointsDTO;
import com.rewards.dto.CustomerRewardsRequest;
import com.rewards.dto.ResponseDTO;
import com.rewards.exception.ServiceError;
import com.rewards.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/{id}/rewards")
public class CustomerRewardsController {

    @Autowired
    private final RewardService rewardService;

    public CustomerRewardsController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<CustomerRewardPointsDTO>> getRewardPointsForCustomer(@PathVariable Long id, @RequestBody CustomerRewardsRequest requestBody){
        ResponseDTO<CustomerRewardPointsDTO> responseDTO = new ResponseDTO();
        if(CollectionUtils.isEmpty(requestBody.getRewardNames())){
            ServiceError error = getServiceError("atleast one reward should be apply", HttpStatus.BAD_REQUEST.name());
            responseDTO.setError(error);
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }

        if(requestBody.getRewardsForNoOfMonths()>11){
            ServiceError error = getServiceError("no Of months should be less than 11", HttpStatus.BAD_REQUEST.name());
            responseDTO.setError(error);
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }

        CustomerRewardPointsDTO rewardPointsForCustomer = rewardService.getRewardPointsForCustomer(id, requestBody.getRewardNames(), requestBody.getRewardsForNoOfMonths());

        responseDTO.setData(rewardPointsForCustomer);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    private ServiceError getServiceError(String message, String status) {
        ServiceError error = ServiceError.builder().message(message).statusCode(status).build();
        return error;
    }
}
