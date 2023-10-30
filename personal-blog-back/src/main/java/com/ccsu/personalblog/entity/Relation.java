package com.ccsu.personalblog.entity;

import lombok.Data;

@Data
public class Relation {
    String id;
    String followId;
    String followedId;
    Integer relationFlag;
}
