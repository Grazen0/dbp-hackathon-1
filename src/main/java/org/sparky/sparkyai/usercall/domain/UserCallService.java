package org.sparky.sparkyai.usercall.domain;

import java.time.ZonedDateTime;
import java.util.List;

import org.sparky.sparkyai.ai.domain.AiService;
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
    private final AiService aiService;

    public List<UserCall> getCallsByCompanyId(Long companyId) {
        return userCallRepository.findByCompanyId(companyId);
    }

    public List<UserCall> getCallsAfterDate(ZonedDateTime start) {
        return userCallRepository.findByCreatedAtGreaterThanEqual(start);
    }

    public UserCall createUserCall(User user, String prompt, String response) {
        int tokens = aiService.tokenCount(prompt) + aiService.tokenCount(response);
        UserCall call = new UserCall();
        call.setUser(user);
        call.setCompany(user.getCompany());
        call.setPrompt(prompt);
        call.setResponse(response);
        call.setConsumedTokens(tokens);
        call.setWasError(false);
        return userCallRepository.save(call);
    }

}
