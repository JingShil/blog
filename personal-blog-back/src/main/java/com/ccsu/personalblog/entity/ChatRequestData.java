package com.ccsu.personalblog.entity;


import com.ccsu.personalblog.global.ChatGlobal;
import lombok.Data;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Data
public class ChatRequestData {
    private String model = ChatGlobal.MODEL_GPT_TURBO35;
    private Messages messages;
    private boolean stream = ChatGlobal.STREAM;
    private SseEmitter sseEmitter;

    @Data
    private class Messages {
        private String role = ChatGlobal.ROLE;
        private String content;
    }

    public void setMessages(String content) {
        this.messages = new Messages();
        this.messages.setContent(content);
    }

    public void setMessages(String role, String content) {
        this.messages = new Messages();
        this.messages.setContent(content);
        this.messages.setRole(role);
    }

    public String getRole() {
        return this.messages.getRole();
    }

    public String getContent() {
        return this.messages.getContent();
    }

}
