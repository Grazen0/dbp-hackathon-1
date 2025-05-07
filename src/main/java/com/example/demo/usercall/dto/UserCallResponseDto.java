package com.example.demo.usercall.dto;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class UserCallResponseDto {

    private String prompt;
    private String response;
    private Long consumedTokens;
    private ZonedDateTime createdAt;

}
