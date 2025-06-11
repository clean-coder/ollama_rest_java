package json;

import java.util.ArrayList;
import java.util.Collections;
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

    public static ChatRequest create(String model) {
        return new ChatRequest(model,
                List.of(),
                false);
    }

    public static ChatRequest addMessage(ChatRequest oldRequest, Message newMessage) {
        var allMessages = new ArrayList<>(oldRequest.messages());
        allMessages.add(newMessage);

        return new ChatRequest(oldRequest.model(),
                Collections.unmodifiableList(allMessages),
                false);
    }
}

