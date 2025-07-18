package helper;

import json.ChatRequest;
import json.Message;

import java.util.ArrayList;
import java.util.List;

import static json.Roles.ASSISTANT;
import static json.Roles.USER;

public class Context {
    private final String modelName;
    private final List<Message> data;

    public Context(String modelName) {
        this.modelName = modelName;
        this.data = new ArrayList<>();
    }

    public ChatRequest createChatRequest() {
        return new ChatRequest(modelName, data, false);
    }

    public void addRequestMessage(String question) {
        var newMessage = new Message(USER, question);
        this.data.add(newMessage);
    }

    public void addResponseMessage(String response) {
        var newMessage = new Message(ASSISTANT, response);
        this.data.add(newMessage);
    }

    @Override
    public String toString() {
        return "Context{" +
               "modelName='" + modelName + '\'' +
               ", data=" + data +
               '}';
    }
}
