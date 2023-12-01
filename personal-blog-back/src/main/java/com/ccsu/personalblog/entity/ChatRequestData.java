package com.ccsu.personalblog.entity;


import com.ccsu.personalblog.global.ChatGlobal;
import lombok.Data;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Data
public class ChatRequestData {
    private String model = ChatGlobal.MODEL_GPT_TURBO35;
    private List<Message> messages;
    private boolean stream = ChatGlobal.STREAM;






}
