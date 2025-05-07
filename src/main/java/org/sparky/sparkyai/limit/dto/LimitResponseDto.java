package org.sparky.sparkyai.limit.dto;

import org.sparky.sparkyai.limit.domain.LimitType;

import lombok.Data;

@Data
public class LimitResponseDto {

    private LimitType type;
    private Integer value;

}
