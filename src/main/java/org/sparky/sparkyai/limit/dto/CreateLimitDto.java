package org.sparky.sparkyai.limit.dto;

import java.time.Duration;

import org.sparky.sparkyai.limit.domain.LimitType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateLimitDto {

    @NotNull
    private LimitType type;

    @NotNull
    @Min(1)
    private Integer value;

    @NotNull
    private Duration timeWindow;

    @NotNull
    private Duration duration;
}
