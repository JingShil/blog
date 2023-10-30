package com.ccsu.personalblog.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Code {
    private String id;
    private String code;
    private LocalDateTime createTime;
}
