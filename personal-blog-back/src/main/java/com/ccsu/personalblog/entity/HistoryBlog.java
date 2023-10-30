package com.ccsu.personalblog.entity;


import cn.hutool.core.date.DateTime;
import lombok.Data;

@Data
public class HistoryBlog {

    private String id;
    private String userId;
    private String BlogId;
    private DateTime updateTime;
}
