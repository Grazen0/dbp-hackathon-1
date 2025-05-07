package com.example.demo.user.dto;

import java.util.List;

import com.example.demo.usercall.dto.UserCallResponseDto;
import com.example.demo.limit.dto.LimitResponseDto;
import com.example.demo.user.domain.Role;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String username;
    private Role role;
    private List<UserCallResponseDto> requestHistory;
    private List<LimitResponseDto> limits;

}
