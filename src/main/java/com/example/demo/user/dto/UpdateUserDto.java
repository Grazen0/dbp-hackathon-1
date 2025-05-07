package com.example.demo.user.dto;

import com.example.demo.user.domain.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserDto {

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    private Role role;
}
