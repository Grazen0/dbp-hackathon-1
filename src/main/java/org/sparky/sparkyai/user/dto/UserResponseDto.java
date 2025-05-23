package org.sparky.sparkyai.user.dto;

import java.util.List;

import org.sparky.sparkyai.usercall.dto.UserCallResponseDto;
import org.sparky.sparkyai.limit.dto.LimitResponseDto;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String username;
    private List<UserCallResponseDto> callHistory;
    private List<LimitResponseDto> limits;

}
