package com.ccsu.personalblog.entity;

import lombok.Data;

@Data
public class Blog {
    private BlogArticle blogArticle;
    private String name;
    private String userId;
    private String imag;
}
