package com.ccsu.personalblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccsu.personalblog.common.R;
import com.ccsu.personalblog.entity.Relation;
import com.ccsu.personalblog.service.RelationService;
import com.ccsu.personalblog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("relation")
public class RelationController {

    @Autowired
    private RelationService relationService;

    @PostMapping("/follow")
    public R<String> follow(@RequestBody Map<String, String> ids) {
        String followId = ids.get("followId");
        String followedId = ids.get("followedId");
        LambdaQueryWrapper<Relation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Relation::getFollowedId, followId).eq(Relation::getFollowId, followedId);
        Relation one = relationService.getOne(queryWrapper);
        if(one == null) {
            LambdaQueryWrapper<Relation> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(Relation::getFollowId, followId).eq(Relation::getFollowedId, followedId);
            Relation one1 = relationService.getOne(queryWrapper1);
            if(one1 == null) {
                //如果两者没有任何关系
                one = new Relation();
                String id = new MD5Utils().encryptMD5(followId + followedId);
                one.setId(id);
                one.setFollowId(followId);
                one.setFollowedId(followedId);
                one.setRelationFlag(1);
                relationService.save(one);
                return R.success("已关注");
            }else if(one1.getRelationFlag() == 3){
                //如果用户已经拉黑了要关注的博主
                one1.setRelationFlag(1);
                relationService.updateById(one1);
                return R.success("已关注");
            }else if(one1.getRelationFlag() == 4){
                return R.error("该用户已将你拉黑");
            }else {
                return R.error("错误");
            }
        }else {
            if(one.getRelationFlag() == 3) {
                return R.error("该用户已将你拉黑");
            }else if(one.getRelationFlag() == 1){
                one.setRelationFlag(2);
                relationService.updateById(one);
                return R.success("已关注");
            }else if(one.getRelationFlag() == 4){
                return R.error("该用户已将你拉黑");
            }else {
                return R.error("错误");
            }
        }
    }



    @PostMapping("unfollow")
    public R<String> unfollow(@RequestBody Map<String, String> ids) {
        String followId = ids.get("followId");
        String followedId = ids.get("followedId");
        Relation one = relationService.matching(followId, followedId);
        if(one == null) {
            Relation one1 = relationService.matching(followedId, followId);
            if(one1 == null) {
                return R.error("错误");
            }else if(one1.getRelationFlag() == 2){
                one1.setRelationFlag(1);
                relationService.updateById(one1);
                return R.success("已取消关注");
            }else {
                return R.error("错误");
            }
        }else {
            if(one.getRelationFlag() == 2) {
                one.setFollowId(followedId);
                one.setFollowedId(followId);
                one.setRelationFlag(1);
                relationService.updateById(one);
                return R.success("已取消关注");
            }else if(one.getRelationFlag() == 1) {
                relationService.removeById(one);
                return R.success("已取消关注");
            }else {
                return R.success("错误");
            }
        }
    }


    @PostMapping("cancel")
    public R<String> cancel(@RequestBody Map<String, String> ids) {
        String followId = ids.get("followId");
        String followedId = ids.get("followedId");
        Relation one = relationService.matching(followId, followedId);
        if(one == null) {
            Relation one1 = relationService.matching(followedId, followId);
            if(one1 == null) {
                relationService.add(followId, followedId, 3);
                return R.success("已拉黑");
            }else {
                if(one1.getRelationFlag() == 1 || one1.getRelationFlag() == 2) {
                   relationService.update(one1, one1.getFollowedId(), one1.getFollowId(), 3);
                   return R.success("已拉黑");
                }else if(one1.getRelationFlag() == 3) {
                    relationService.update(one1, one1.getFollowId(), one1.getFollowedId(), 4);
                    return R.success("已拉黑");
                }else {
                    return R.error("错误");
                }
            }
        }else {
            if(one.getRelationFlag() == 1 || one.getRelationFlag() == 2) {
                relationService.update(one, one.getFollowedId(), one.getFollowId(), 3);
                return R.success("已拉黑");
            }else {
                return R.error("错误");
            }
        }
    }

    @PostMapping("uncancel")
    public R<String> uncancel(@RequestBody Map<String, String> ids) {
        String followId = ids.get("followId");
        String followedId = ids.get("followedId");
        Relation one = relationService.matching(followId, followedId);
        if(one == null) {
            Relation one1 = relationService.matching(followedId, followId);
            if(one1 == null) {
                return R.error("错误");
            }else {
                if(one1.getRelationFlag() == 4) {
                    relationService.update(one1, one1.getFollowId(), one1.getFollowedId(), 3);
                    return R.success("已取消");
                }else {
                    return R.error("错误");
                }
            }
        }else {
            if(one.getRelationFlag() == 3) {
                relationService.removeById(one);
                return R.success("已取消");
            }else if(one.getRelationFlag() == 4){
                relationService.update(one, one.getFollowedId(), one.getFollowId(), 3);
                return R.success("已取消");
            }else {
                return R.error("错误");
            }
        }
    }



}
