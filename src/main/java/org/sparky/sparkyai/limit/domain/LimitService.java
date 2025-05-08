package org.sparky.sparkyai.limit.domain;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import org.sparky.sparkyai.limit.dto.CreateLimitDto;
import org.sparky.sparkyai.user.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LimitService {

    private ModelMapper modelMapper;

    public Limit createUserLimit(User user, CreateLimitDto limitDto) {
        Limit limit = modelMapper.map(limitDto, Limit.class);
        limit.setUser(user);
        return limit;
    }

}
