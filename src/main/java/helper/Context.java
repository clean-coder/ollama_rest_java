package helper;

import json.ChatRequest;
import json.Message;

import static json.Roles.ASSISTANT;
import static json.Roles.USER;

public class Context {
    private final String modelName;
    private ChatRequest data;

    public Context(String modelName) {
        this.modelName = modelName;
        this.data = ChatRequest.create(modelName);
    }

    public ChatRequest getData() {
        return data;
    }

    public void addRequestMessage(String question) {
        var newMessage = new Message(USER, question);
        this.data = ChatRequest.addMessage(this.data, newMessage);
    }

    public void addResponseMessage(String response) {
        var newMessage = new Message(ASSISTANT, response);
        this.data = ChatRequest.addMessage(this.data, newMessage);
    }

    @Override
    public String toString() {
        return "Context{" +
               "modelName='" + modelName + '\'' +
               ", data=" + data +
               '}';
    }
}
