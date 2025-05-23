package org.sparky.sparkyai.ai.domain;

import com.azure.ai.inference.ChatCompletionsClient;
import com.azure.ai.inference.ChatCompletionsClientBuilder;
import com.azure.ai.inference.models.*;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AiService {

    @Value("${github.token}")
    private String github_token;

    private String getResponse(String model, ChatCompletionsOptions request) {

        String endpoint = "https://models.github.ai/inference";

        ChatCompletionsClient client = new ChatCompletionsClientBuilder()
                .credential(new AzureKeyCredential(github_token))
                .endpoint(endpoint)
                .buildClient();

        request.setModel(model);

        ChatCompletions completions = client.complete(request);

        return completions.getChoices().getFirst().getMessage().getContent();
    }

    public String chatRequest(String model, String requestText) {

        ChatCompletionsOptions chatCompletionsOptions = new ChatCompletionsOptions(
                Collections.singletonList(new ChatRequestUserMessage(requestText)));

        return getResponse(model, chatCompletionsOptions);

    }

    public String completionRequest(String model, String requestText) {
        List<ChatRequestMessage> chatMessages = Arrays.asList(
                new ChatRequestSystemMessage(
                        "Help me complete the text fragment that I will send in the next message. In your response, send me only the original text plus the completed text, with no additional comments. Do not respond to this message."),
                new ChatRequestUserMessage(requestText));

        ChatCompletionsOptions chatCompletionsOptions = new ChatCompletionsOptions(chatMessages);
        return getResponse(model, chatCompletionsOptions);
    }

    public int tokenCount(String message) {
        return message.trim().split("\\W+").length;
    }

}
