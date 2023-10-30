package com.ccsu.personalblog.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;

@Data
public class LikeBlog {

    private String id;
    private String userId;
    private String BlogId;
    private DateTime createTime;
}
