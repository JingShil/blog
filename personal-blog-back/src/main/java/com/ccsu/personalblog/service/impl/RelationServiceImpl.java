package com.ccsu.personalblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.personalblog.entity.Relation;
import com.ccsu.personalblog.mapper.RelationMapper;
import com.ccsu.personalblog.service.RelationService;
import com.ccsu.personalblog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationServiceImpl extends ServiceImpl<RelationMapper, Relation> implements RelationService {

    @Autowired
    RelationService relationService;

    @Override
    public Relation matching(String followId, String followedId) {
        LambdaQueryWrapper<Relation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Relation::getFollowId, followId).eq(Relation::getFollowedId, followedId);
        return relationService.getOne(queryWrapper);
    }

    @Override
    public void add(String followId, String followedId, Integer relationFlag) {
        Relation relation = new Relation();
        String id = new MD5Utils().encryptMD5(followId + followedId);
        relation.setId(id);
        relation.setFollowId(followId);
        relation.setFollowedId(followedId);
        relation.setRelationFlag(relationFlag);
        relationService.save(relation);
    }

    @Override
    public void update(Relation relation, String followId, String followedId, Integer relationFlag) {
        relation.setFollowId(followId);
        relation.setFollowedId(followedId);
        relation.setRelationFlag(relationFlag);
        relationService.updateById(relation);
    }
}
