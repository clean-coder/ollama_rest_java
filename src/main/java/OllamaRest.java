import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.ChatRequest;
import json.ChatResponse;
import json.ModelList;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class OllamaRest {
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new GsonBuilder().create();

    public ModelList list_models() {
        var url = "http://localhost:11434/api/tags";
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return gson.fromJson(response.body(), ModelList.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public ChatResponse chat(String modelName, String question) {
        var url = "http://localhost:11434/api/chat";
        var data = ChatRequest.create(modelName, question);
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(data)))
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
        var ollamaRest = new OllamaRest();

        var modelList = ollamaRest.list_models();
        modelList.models().forEach(System.out::println);

        var responseFrance = ollamaRest.chat("llama3.2", "What is the capital of France?");
        System.out.println(responseFrance);
    }
}
