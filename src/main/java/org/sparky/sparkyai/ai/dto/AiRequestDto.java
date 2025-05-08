package org.sparky.sparkyai.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AiRequestDto {

    @NotNull
    @NotBlank
    private String prompt;

}
