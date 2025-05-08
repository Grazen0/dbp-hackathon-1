package org.sparky.sparkyai.usercall.domain;

import java.time.ZonedDateTime;
import java.util.List;

import org.sparky.sparkyai.user.domain.User;
import org.sparky.sparkyai.usercall.infrastructure.UserCallRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCallService {

    private final UserCallRepository userCallRepository;

    public List<UserCall> getCallsByCompanyId(Long companyId) {
        return userCallRepository.findByCompanyId(companyId);
    }

    public List<UserCall> getCallsAfterDate(ZonedDateTime start) {
        return userCallRepository.findByCreatedAtGreaterThanEqual(start);
    }

    public UserCall createUserCall(User user) {
        UserCall call = new UserCall();
        call.setUser(user);
        call.setCompany(user.getCompany());
        call.setPrompt("hello");
        call.setResponse("world");
        call.setConsumedTokens(10);
        call.setWasError(false);
        return userCallRepository.save(call);
    }

}
