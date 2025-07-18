package json;

import java.util.List;

import static json.Roles.USER;

public record ChatRequest(String model,
                          List<Message> messages,
                          boolean stream) {

    public static ChatRequest create(String model, String question) {
        return new ChatRequest(model,
                List.of(new Message(USER, question)),
                false);
    }
}

