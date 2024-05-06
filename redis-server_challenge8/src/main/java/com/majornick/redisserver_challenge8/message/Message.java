package com.majornick.redisserver_challenge8.message;

import java.util.List;
import java.util.Objects;

public class Message {
    private Type type;
    private String content;
    private List<Message> messages;

    public Message(Type type, String content) {
        this.type = type;
        this.content = content;
    }


    public Message() {
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return type == message.type && Objects.equals(content, message.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, content);
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}
