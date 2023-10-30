package com.ccsu.personalblog.entity;


import lombok.Data;

import java.util.List;

@Data
public class ChatResponseData {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;

    // getters and setters
    @Data
    public static class Choice {
        private int index;
        private Delta delta;
        private String finish_reason;

        // getters and setters
    }
    @Data
    public static class Delta {
        private String content;
        private String role;
        // getters and setters
    }
}



