package com.rewards.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rewards.exception.ServiceError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO <T>{
    @JsonProperty("data")
    private T data;
    private ServiceError error;
}
