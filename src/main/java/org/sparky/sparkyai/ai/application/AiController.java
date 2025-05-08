package org.sparky.sparkyai.ai.application;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/chat")
    public ResponseEntity<String> chatAi(@RequestBody String message) {
        return aiService.createRequest("meta/Llama-4-Scout-17B-16E-Instruct", message);
    }

    @PostMapping("/completion")
    public ResponseEntity<String> ccompletionAi(@RequestBody String message) {
        return aiService.createRequest("meta/Llama-4-Scout-17B-16E-Instruct", message);
    }

    @PostMapping("/multimodal")
    public ResponseEntity<String> multimodalAi(@RequestBody String message) {
        return aiService.createRequest("meta/Llama-4-Scout-17B-16E-Instruct", message);
    }

}
