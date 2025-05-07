package com.example.demo.auth.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.common.exception.UnauthorizedException;
import com.example.demo.jwt.domain.JwtService;
import com.example.demo.jwt.dto.JwtAuthLoginDto;
import com.example.demo.jwt.dto.JwtAuthResponseDto;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthResponseDto jwtLogin(JwtAuthLoginDto loginDto) {
        User user = userService.getUserByUsername(loginDto.getUsername());

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid username or password");
        }

        JwtAuthResponseDto response = new JwtAuthResponseDto();
        response.setToken(jwtService.generateToken(user));
        return response;
    }

}
