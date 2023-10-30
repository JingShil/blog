package com.ccsu.personalblog.controller;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccsu.personalblog.common.R;
import com.ccsu.personalblog.entity.Comment;
import com.ccsu.personalblog.service.CommentService;
import com.ccsu.personalblog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    private MD5Utils md5Utils = new MD5Utils();

    @Autowired
    private CommentService commentService;


    @PostMapping("/add")
    public R<String> add(@RequestBody Comment comment) {
        DateTime now = DateTime.now();
        if(StringUtils.isEmpty(comment.getParentId())) {
            comment.setParentId("0");
        }
        String id = md5Utils.encryptMD5(comment.getParentId() + comment.getBlogId() +
                comment.getUserId() + now.toString() + comment.getText());
        comment.setId(id);
        comment.setCreateTime(now);
        commentService.save(comment);
        return R.success("评论成功");
    }

    @PostMapping("update")
    public R<String> update(@RequestBody Comment comment) {
        comment.setCreateTime(DateTime.now());
        commentService.updateById(comment);
        return R.success("修改成功");
    }

    @PostMapping("/delete")
    public R<String> delete(@RequestBody Comment comment) {
        commentService.removeById(comment);
        return R.success("删除成功");
    }

    @PostMapping("/list")
    public R<List<List<Comment>>> list(@RequestBody String blogId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getBlogId, blogId).eq(Comment::getParentId, 0).orderByAsc(Comment::getCreateTime);
        List<Comment> list = commentService.list(queryWrapper);
        if(list == null) {
            return R.success(null);
        }
        List<List<Comment>> commentLList = new ArrayList<>();
        for(Comment comment : list) {
            List<Comment> comments = new ArrayList<>();
            LambdaQueryWrapper<Comment> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(Comment::getParentId, comment.getId()).orderByAsc(Comment::getCreateTime);
            List<Comment> list1 = commentService.list(queryWrapper1);
            comments.add(comment);
            comments.addAll(list1);
            commentLList.add(comments);
        }
        return R.success(commentLList);
    }
}
