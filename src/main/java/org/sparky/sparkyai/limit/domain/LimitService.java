package org.sparky.sparkyai.limit.domain;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import org.sparky.sparkyai.common.exception.TooManyRequestsException;
import org.sparky.sparkyai.limit.dto.CreateLimitDto;
import org.sparky.sparkyai.limit.infrastructure.LimitRepository;
import org.sparky.sparkyai.user.domain.User;
import org.sparky.sparkyai.user.domain.UserService;
import org.sparky.sparkyai.usercall.domain.UserCall;
import org.sparky.sparkyai.usercall.domain.UserCallService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LimitService {

    private final ModelMapper modelMapper;
    private final LimitRepository limitRepository;
    private final UserCallService userCallService;;
    private final UserService userService;

    public Limit createUserLimit(User user, CreateLimitDto limitDto) {
        Limit limit = modelMapper.map(limitDto, Limit.class);
        limit.setUser(user);
        return limitRepository.save(limit);
    }

    public void applyLimits(User user) {
        ZonedDateTime now = ZonedDateTime.now();

        if (user.getLimitedUntil() != null && now.isBefore(user.getLimitedUntil())) {
            throw new TooManyRequestsException("Limited until " + user.getLimitedUntil());
        }

        for (Limit limit : user.getLimits()) {
            ZonedDateTime windowStart = now.minus(limit.getTimeWindow());
            List<UserCall> callsInWindow = userCallService.getCallsAfterDate(windowStart);
            int value;

            switch (limit.getType()) {
                case CALLS:
                    value = callsInWindow.size();
                    break;
                case TOKENS:
                    value = callsInWindow.stream().map(call -> call.getConsumedTokens()).reduce(0, Integer::sum);
                    break;
                default:
                    throw new RuntimeException("Invalid limit type");
            }

            if (value >= limit.getValue()) {
                // Apply rate limit
                userService.limitUser(user, now.plus(limit.getDuration()));
                throw new TooManyRequestsException("Rate limit exceeded. Limited until " + user.getLimitedUntil());
            }
        }
    }
}
