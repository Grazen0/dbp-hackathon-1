package org.sparky.sparkyai.user.dto;

import java.util.List;

import org.sparky.sparkyai.usercall.dto.UserCallResponseDto;
import org.sparky.sparkyai.limit.dto.LimitResponseDto;
import org.sparky.sparkyai.user.domain.Role;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String username;
    private Role role;
    private List<UserCallResponseDto> requestHistory;
    private List<LimitResponseDto> limits;

}
