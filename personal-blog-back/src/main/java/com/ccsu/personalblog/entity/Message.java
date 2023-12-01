package com.ccsu.personalblog.entity;

import com.ccsu.personalblog.global.ChatGlobal;
import lombok.Data;

@Data
public class Message {
    private String role = ChatGlobal.ROLE;
    private String content;
}
