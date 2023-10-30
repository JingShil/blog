package com.ccsu.personalblog.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

@Data
public class CollectBlog {

    private String id;
    private String userId;
    private String BlogId;
    private DateTime createTime;
}
