package com.ccsu.personalblog.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogArticle {

    private String id;
    private String userId;
    private String text;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer likeNum;
    private Integer favoriteNum;
    private Integer deleteFlag;
}
