package com.ccsu.personalblog.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class User {
    private String id;
    private String phone;
    private String name;
    private String password;
    private Integer sex;
    private Date birthday;
    private Integer age;
    private LocalDateTime createTime;

}
