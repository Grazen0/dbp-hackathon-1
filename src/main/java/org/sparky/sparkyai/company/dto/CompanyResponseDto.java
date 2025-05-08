package org.sparky.sparkyai.company.dto;

import java.time.ZonedDateTime;
import java.util.List;

import org.sparky.sparkyai.company.domain.Status;
import org.sparky.sparkyai.user.dto.AdminResponseDto;
import org.sparky.sparkyai.user.dto.UserResponseDto;

import lombok.Data;

@Data
public class CompanyResponseDto {

    private Long id;
    private String name;
    private String ruc;
    private AdminResponseDto admin;
    private List<UserResponseDto> users;
    private Status status;
    private ZonedDateTime affiliationDate;

}
