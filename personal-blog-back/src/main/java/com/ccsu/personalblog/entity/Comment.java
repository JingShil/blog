package com.ccsu.personalblog.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;

@Data
public class Comment {
    private String id;
    private String userId;
    private String parentId;
    private String blogId;
    private String text;
    private DateTime createTime;
}
