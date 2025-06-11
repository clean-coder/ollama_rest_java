package json;

public record ChatResponse(
        String model,
        Message message
) {

    @Override
    public String toString() {
        return "(" + model + ") " + message.content();
    }
}