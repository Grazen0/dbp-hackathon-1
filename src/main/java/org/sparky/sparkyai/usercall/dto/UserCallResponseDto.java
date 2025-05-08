package org.sparky.sparkyai.usercall.dto;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class UserCallResponseDto {

    private String prompt;
    private String response;
    private Boolean wasError;
    private Integer consumedTokens;
    private ZonedDateTime createdAt;

}
