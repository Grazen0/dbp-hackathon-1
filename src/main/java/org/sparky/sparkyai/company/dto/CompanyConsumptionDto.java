package org.sparky.sparkyai.company.dto;

import java.util.List;

import org.sparky.sparkyai.usercall.dto.UserCallResponseDto;

import lombok.Data;

@Data
public class CompanyConsumptionDto {

    private List<UserCallResponseDto> callHistory;
    private Integer totalCalls;
    private Integer totalConsumedTokens;

}
