package org.sparky.sparkyai.auth.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.sparky.sparkyai.auth.domain.AuthService;
import org.sparky.sparkyai.jwt.dto.JwtAuthLoginDto;
import org.sparky.sparkyai.jwt.dto.JwtAuthResponseDto;
import org.sparky.sparkyai.user.dto.CreateUserDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public JwtAuthResponseDto login(@Valid @RequestBody JwtAuthLoginDto loginDto) {
        return authService.jwtLogin(loginDto);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtAuthResponseDto register(@Valid @RequestBody CreateUserDto registerDto) {
        return authService.jwtRegisterSparkyAdmin(registerDto);
    }

}
