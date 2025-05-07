package org.sparky.sparkyai.ai.application;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.sparky.sparkyai.ai.domain.AiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@PreAuthorize("hasRole('ROLE_USER')")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

}
