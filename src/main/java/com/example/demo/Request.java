package com.example.demo;

import com.azure.ai.inference.ChatCompletionsClient;
import com.azure.ai.inference.ChatCompletionsClientBuilder;
import com.azure.ai.inference.models.*;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.Configuration;

import java.util.Arrays;
import java.util.List;

public class Request {

    private final String requestText;

    private final String model;

    public Request(String requestText, String model) {
        this.requestText = requestText;
        this.model = model;
    }

    public String createRequest() {

        String key = Configuration.getGlobalConfiguration().get("GITHUB_TOKEN");
        String endpoint = "https://models.github.ai/inference";

        ChatCompletionsClient client = new ChatCompletionsClientBuilder()
                .credential(new AzureKeyCredential(key))
                .endpoint(endpoint)
                .buildClient();

        List<ChatRequestMessage> chatMessages = Arrays.asList(
                new ChatRequestSystemMessage("You are a helpful assistant."),
                new ChatRequestUserMessage(requestText));

        ChatCompletionsOptions chatCompletionsOptions = new ChatCompletionsOptions(chatMessages);
        chatCompletionsOptions.setModel(model);

        ChatCompletions completions = client.complete(chatCompletionsOptions);

        return completions.getChoices().getFirst().getMessage().getContent();

    }

}
