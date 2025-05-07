package com.example.demo.company.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeCompanyStatusDto {

    @NotNull
    private Boolean enabled;

}
