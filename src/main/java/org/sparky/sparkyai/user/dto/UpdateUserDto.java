package org.sparky.sparkyai.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserDto {

    @NotNull
    @NotBlank
    private String username;

}
