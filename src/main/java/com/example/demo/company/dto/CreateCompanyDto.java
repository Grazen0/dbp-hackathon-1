package com.example.demo.company.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCompanyDto {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String ruc;

}
