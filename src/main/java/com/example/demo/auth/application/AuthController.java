package com.example.demo.auth.application;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.domain.AuthService;
import com.example.demo.jwt.dto.JwtAuthLoginDto;
import com.example.demo.jwt.dto.JwtAuthResponseDto;

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

}
