package com.ccsu.personalblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccsu.personalblog.entity.Relation;

public interface RelationService extends IService<Relation> {

    public Relation matching (String followId, String followedId);


    public void add(String followId, String followedId, Integer relationFlag);

    public void update(Relation relation, String followId, String followedId, Integer relationFlag);
}
