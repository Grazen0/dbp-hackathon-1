package org.sparky.sparkyai.limit.dto;

import java.time.Duration;

import org.sparky.sparkyai.limit.domain.LimitType;

import lombok.Data;

@Data
public class LimitResponseDto {

    private Long id;
    private LimitType type;
    private Integer value;
    private Duration timeWindow;
    private Duration duration;

}
