package fr.ensim.interop.introrest.model.bot;

public class MessageObject {

    private String chat_id;
    private String text;

    public MessageObject(String chat_id, String text) {
        this.chat_id = chat_id;
        this.text = text;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "MessageObject{" +
                "chat_id='" + chat_id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
