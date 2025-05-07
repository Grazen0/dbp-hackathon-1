package org.sparky.sparkyai.user.dto;

import java.util.List;

import org.sparky.sparkyai.usercall.dto.UserCallResponseDto;

import lombok.Data;

@Data
public class UserConsumptionDto {

    private List<UserCallResponseDto> callHistory;
    private Integer totalCalls;
    private Integer totalConsumedTokens;

}
