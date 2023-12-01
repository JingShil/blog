package com.ccsu.personalblog.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatHistory {
    private String id;
    private String userId;
    private String text;
    private LocalDateTime updateTime;
}
