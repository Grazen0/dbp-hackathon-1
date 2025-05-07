package com.example.demo.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserDto {

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;

}
