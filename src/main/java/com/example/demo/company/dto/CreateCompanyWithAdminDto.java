package com.example.demo.company.dto;

import com.example.demo.user.dto.CreateUserDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCompanyWithAdminDto {

    @NotNull
    @Valid
    CreateCompanyDto company;

    @NotNull
    @Valid
    CreateUserDto mainAdmin;
}
