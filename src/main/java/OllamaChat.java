import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import helper.Context;
import json.ChatRequest;
import json.ChatResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class OllamaChat {

    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new GsonBuilder().create();

    public List<ChatResponse> chat(String modelName, List<String> questions) {
        var responses = new ArrayList<ChatResponse>();
        for (var question : questions) {
            var chatRequest = ChatRequest.create(modelName, question);
            var response = sendRequest(chatRequest);
            responses.add(response);
        }
        return responses;
    }

    public List<ChatResponse> chatWithContext(String modelName, List<String> questions) {
        var context = new Context(modelName);
        var responses = new ArrayList<ChatResponse>();

        for (var question : questions) {
            context.addRequestMessage(question);
            System.out.println("  Context: " + context);
            var response = sendRequest(context.getData());
            responses.add(response);
            context.addResponseMessage(response.message().content());
        }
        return responses;
    }

    private ChatResponse sendRequest(ChatRequest chatRequest) {
        var url = "http://localhost:11434/api/chat";
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(chatRequest)))
                .build();
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Error: " + response.statusCode() + ": " + response.body());
            }
            return gson.fromJson(response.body(), ChatResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        var ollamaChat = new OllamaChat();

        var responses = ollamaChat.chat("llama3.2", List.of("What is the capital of France?", "And Sweden?"));
        responses.forEach(System.out::println);

        responses = ollamaChat.chatWithContext("llama3.2", List.of("What is the capital of France?", "And Sweden?"));
        responses.forEach(System.out::println);
    }
}
