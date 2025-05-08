package org.sparky.sparkyai.ai.application;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.sparky.sparkyai.ai.domain.AiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@PreAuthorize("hasRole('ROLE_USER')")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/chat")
    public ResponseEntity<String> chatAi(@RequestParam(defaultValue = "meta/Llama-4-Scout-17B-16E-Instruct") String model, @RequestBody String message) {
        return aiService.chatRequest(model, message);
    }

    @PostMapping("/completion")
    public ResponseEntity<String> completionAi(@RequestParam(defaultValue = "meta/Llama-4-Scout-17B-16E-Instruct") String model, @RequestBody String message) {
        return aiService.completionRequest(model, message);
    }

    @PostMapping("/multimodal")
    public ResponseEntity<String> multimodalAi(@RequestParam(defaultValue = "openai/gpt-4.1") String model, @RequestBody String message) {
        return aiService.chatRequest(model, message);
    }

}
