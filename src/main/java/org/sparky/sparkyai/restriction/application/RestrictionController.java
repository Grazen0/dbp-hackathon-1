package org.sparky.sparkyai.restriction.application;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.sparky.sparkyai.restriction.domain.Restriction;
import org.sparky.sparkyai.restriction.domain.RestrictionService;
import org.sparky.sparkyai.restriction.dto.CreateRestrictionDto;
import org.sparky.sparkyai.restriction.dto.RestrictionResponseDto;
import org.sparky.sparkyai.restriction.dto.UpdateRestrictionDto;
import org.sparky.sparkyai.user.domain.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/company/restrictions")
@PreAuthorize("hasRole('ROLE_COMPANY_ADMIN')")
@RequiredArgsConstructor
public class RestrictionController {

    private final RestrictionService restrictionService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestrictionResponseDto addCompanyRestriction(@Valid @RequestBody CreateRestrictionDto restrictionDto,
            Authentication authentication) {
        User admin = (User) authentication.getPrincipal();
        Restriction restriction = restrictionService.createRestriction(restrictionDto, admin.getCompany());
        return modelMapper.map(restriction, RestrictionResponseDto.class);
    }

    @GetMapping
    public List<RestrictionResponseDto> getCompanyRestrictions(Authentication authentication) {
        User admin = (User) authentication.getPrincipal();
        List<Restriction> restrictions = restrictionService
                .getRestrictionsByCompanyId(admin.getCompany().getId());
        return restrictions.stream()
                .map(restriction -> modelMapper.map(restriction, RestrictionResponseDto.class))
                .toList();
    }

    @PutMapping("/{id}")
    public RestrictionResponseDto updateCompanyRestriction(@PathVariable Long id,
            @Valid @RequestBody UpdateRestrictionDto restrictionDto,
            Authentication authentication) {
        User admin = (User) authentication.getPrincipal();
        Restriction restriction = restrictionService.getRestrictionByIdAndCompanyId(id, admin.getCompany().getId());
        Restriction updatedRestriction = restrictionService.updateRestriction(restriction, restrictionDto);
        return modelMapper.map(updatedRestriction, RestrictionResponseDto.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyRestriction(@PathVariable Long id, Authentication authentication) {
        User admin = (User) authentication.getPrincipal();
        Restriction restriction = restrictionService.getRestrictionByIdAndCompanyId(id, admin.getCompany().getId());
        restrictionService.deleteRestriction(restriction);
    }
}
