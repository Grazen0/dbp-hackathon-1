package org.sparky.sparkyai.user.application;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.sparky.sparkyai.limit.domain.Limit;
import org.sparky.sparkyai.limit.domain.LimitService;
import org.sparky.sparkyai.limit.dto.CreateLimitDto;
import org.sparky.sparkyai.limit.dto.LimitResponseDto;
import org.sparky.sparkyai.user.domain.User;
import org.sparky.sparkyai.user.domain.UserService;
import org.sparky.sparkyai.user.dto.CreateUserDto;
import org.sparky.sparkyai.user.dto.UpdateUserDto;
import org.sparky.sparkyai.user.dto.UserConsumptionDto;
import org.sparky.sparkyai.user.dto.UserResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/company/users")
@PreAuthorize("hasRole('ROLE_COMPANY_ADMIN')")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final LimitService limitService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createCompanyUser(@Valid @RequestBody CreateUserDto userDto,
            Authentication authentication) {
        User admin = (User) authentication.getPrincipal();
        User createdUser = userService.createUser(userDto, admin.getOwnedCompany());
        return modelMapper.map(createdUser, UserResponseDto.class);
    }

    @GetMapping
    public List<UserResponseDto> getAllCompanyUsers(Authentication authentication) {
        User admin = (User) authentication.getPrincipal();
        List<User> users = userService.getUsersByCompanyId(admin.getOwnedCompany().getId());
        return users.stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .toList();
    }

    @GetMapping("/{id}")
    public UserResponseDto getCompanyUser(@PathVariable Long id, Authentication authentication) {
        User admin = (User) authentication.getPrincipal();
        User user = userService.getUserByIdAndCompanyId(id, admin.getOwnedCompany().getId());
        return modelMapper.map(user, UserResponseDto.class);
    }

    @PutMapping("/{id}")
    public UserResponseDto updateCompanyUser(@PathVariable Long id, @Valid @RequestBody UpdateUserDto userDto,
            Authentication authentication) {
        User admin = (User) authentication.getPrincipal();
        User user = userService.getUserByIdAndCompanyId(id, admin.getOwnedCompany().getId());
        User updatedUser = userService.updateUser(user, userDto);
        return modelMapper.map(updatedUser, UserResponseDto.class);
    }

    @PostMapping("/{id}/limits")
    public LimitResponseDto createLimit(@PathVariable Long id, @Valid @RequestBody CreateLimitDto limitDto,
            Authentication authentication) {
        User admin = (User) authentication.getPrincipal();
        User user = userService.getUserByIdAndCompanyId(id, admin.getOwnedCompany().getId());
        Limit limit = limitService.createUserLimit(user, limitDto);
        return modelMapper.map(limit, LimitResponseDto.class);
    }

    @GetMapping("/{id}/consumption")
    public UserConsumptionDto getUserConsumption(@PathVariable Long id, Authentication authentication) {
        User admin = (User) authentication.getPrincipal();
        User user = userService.getUserByIdAndCompanyId(id, admin.getOwnedCompany().getId());
        return userService.getUserConsumption(user);
    }

}
