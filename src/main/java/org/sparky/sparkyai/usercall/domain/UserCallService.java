package org.sparky.sparkyai.usercall.domain;

import java.util.List;

import org.sparky.sparkyai.usercall.infrastructure.UserCallRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCallService {

    private final UserCallRepository userCallRepository;

    public List<UserCall> getCallsByCompanyId(Long companyId) {
        return userCallRepository.findByCompanyId(companyId);
    }

}
