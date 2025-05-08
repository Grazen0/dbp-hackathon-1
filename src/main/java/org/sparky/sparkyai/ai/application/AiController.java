package org.sparky.sparkyai.ai.application;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.sparky.sparkyai.ai.domain.AiService;
import org.sparky.sparkyai.ai.dto.AiRequestDto;
import org.sparky.sparkyai.ai.dto.AiResponseDto;
import org.sparky.sparkyai.ai.dto.ModelResponseDto;
import org.sparky.sparkyai.limit.domain.LimitService;
import org.sparky.sparkyai.user.domain.User;
import org.sparky.sparkyai.usercall.domain.UserCall;
import org.sparky.sparkyai.usercall.domain.UserCallService;
import org.sparky.sparkyai.usercall.dto.UserCallResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@PreAuthorize("hasRole('ROLE_USER')")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;
    private final LimitService limitService;
    private final UserCallService userCallService;
    private final ModelMapper modelMapper;

    @PostMapping("/chat")
    public AiResponseDto chatAi(
            @RequestParam(defaultValue = "meta/Llama-4-Scout-17B-16E-Instruct") String model,
            @Valid @RequestBody AiRequestDto request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        limitService.applyLimits(user);
        String response = aiService.chatRequest(model, request.getPrompt());

        userCallService.createUserCall(user, request.getPrompt(), response);
        return new AiResponseDto(response);

    }

    @PostMapping("/completion")
    public AiResponseDto completionAi(
            @RequestParam(defaultValue = "meta/Llama-4-Scout-17B-16E-Instruct") String model,
            @Valid @RequestBody AiRequestDto request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        limitService.applyLimits(user);
        String response = aiService.completionRequest(model, request.getPrompt());

        userCallService.createUserCall(user, request.getPrompt(), response);
        return new AiResponseDto(response);
    }

    @PostMapping("/multimodal")
    public AiResponseDto multimodalAi(@RequestParam(defaultValue = "openai/gpt-4.1") String model,
            @Valid @RequestBody AiRequestDto request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        limitService.applyLimits(user);
        String response = aiService.chatRequest(model, request.getPrompt());

        userCallService.createUserCall(user, request.getPrompt(), response);
        return new AiResponseDto(response);
    }

    @GetMapping("/models")
    public List<ModelResponseDto> getModels() {
        List<ModelResponseDto> models = new ArrayList<>();
        models.add(new ModelResponseDto("meta/Llama-4-Scout-17B-16E-Instruct"));
        models.add(new ModelResponseDto("openai/gpt-4.1"));
        return models;
    }

    @GetMapping("/history")
    public List<UserCallResponseDto> getHistory(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return user.getCallHistory()
                .stream()
                .map(call -> modelMapper.map(call, UserCallResponseDto.class))
                .toList();
    }

}
