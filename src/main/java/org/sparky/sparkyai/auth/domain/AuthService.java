package org.sparky.sparkyai.auth.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.sparky.sparkyai.common.exception.UnauthorizedException;
import org.sparky.sparkyai.jwt.domain.JwtService;
import org.sparky.sparkyai.jwt.dto.JwtAuthLoginDto;
import org.sparky.sparkyai.jwt.dto.JwtAuthResponseDto;
import org.sparky.sparkyai.user.domain.User;
import org.sparky.sparkyai.user.domain.UserService;

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
